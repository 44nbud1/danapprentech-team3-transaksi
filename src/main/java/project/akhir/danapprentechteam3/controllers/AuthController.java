package project.akhir.danapprentechteam3.controllers;

import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.akhir.danapprentechteam3.models.User;
import project.akhir.danapprentechteam3.payload.request.*;
import project.akhir.danapprentechteam3.payload.response.JwtResponse;
import project.akhir.danapprentechteam3.payload.response.MessageResponse;
import project.akhir.danapprentechteam3.rabbitmq.rabbitconsumer.RabbitMqConsumer;
import project.akhir.danapprentechteam3.rabbitmq.rabbitproducer.RabbitMqProducer;
import project.akhir.danapprentechteam3.readdata.service.ProviderValidation;
import project.akhir.danapprentechteam3.repository.ConfirmationTokenRepository;
import project.akhir.danapprentechteam3.repository.ForgotPasswordRepository;
import project.akhir.danapprentechteam3.repository.UserRepository;
import project.akhir.danapprentechteam3.security.jwt.AuthEntryPointJwt;
import project.akhir.danapprentechteam3.security.jwt.JwtUtils;
import project.akhir.danapprentechteam3.security.passwordvalidation.PasswordAndEmailVal;
import project.akhir.danapprentechteam3.security.services.EmailSenderService;
import project.akhir.danapprentechteam3.security.services.SmsOtpServiceImpl;
import project.akhir.danapprentechteam3.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController<ACCOUNT_AUTH_ID, ACCOUNT_SID> {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	private static String token = null;

	private String plainPassword = null;

	Long count =0L;

	@Autowired
	PasswordAndEmailVal passwordEmailVal;

	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
    PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	ProviderValidation providerValidation;

	@Autowired
	RabbitMqConsumer rabbitMqCustomer;

	@Autowired
	RabbitMqProducer rabbitMqProducer;

	@Autowired
	EmailSenderService emailSenderService;

	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	ForgotPasswordRepository forgotPasswordRepository;

	@Autowired
	SmsOtpServiceImpl smsOtpService;

	//Queue
	private static final String signupKey = "signupKey";

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		//send to rabbi
		rabbitMqProducer.loginSendRabbit(loginRequest);

		//take from rabbit
		LoginRequest login = rabbitMqCustomer.loginRequest(loginRequest);

		//init password
		plainPassword = loginRequest.getPassword();

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getNoTelepon(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		token = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setEmail(userDetails.getEmail());
		jwtResponse.setId(userDetails.getId());
		jwtResponse.setUsername(userDetails.getUsername());
		System.out.println("ini token " +token);
		return ResponseEntity.ok((jwtResponse));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws IOException, TimeoutException {

		// send data to rabbit mq
		rabbitMqProducer.signupSendRabbit(signUpRequest);
		logger.info("Send data to rabbit Mq");

		// take data from rabbit mq
		SignupRequest signup = rabbitMqCustomer.recievedMessage(signUpRequest);

		logger.info("take data from rabbit mq");

		if (!providerValidation.validasiProvider(signup.getNoTelepon()))
		{
			logger.info("ERROR : Username is not registered with the service provider!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Phone number is not registered with the service provider!",
							"400"));
		}

		if (userRepository.existsByNoTelepon(signup.getNoTelepon())) {
			logger.info("ERROR : Username is already taken!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Phone number is already taken!",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorSpace(signup.getPassword())) {
			logger.info("ERROR : Your password does not must contains white space...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password does not must contains white space...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorLowercase(signup.getPassword()))
		{
			logger.info("ERROR : Your password must contains one lowercase characters...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one lowercase characters...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorUpercase(signup.getPassword()))
		{
			logger.info("ERROR : Your password must contains one uppercase characters...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one uppercase characters...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorSymbol(signup.getPassword()))
		{
			logger.info("ERROR : Your password must contains one symbol characters...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one symbol characters...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorNumber(signup.getPassword()))
		{
			logger.info("ERROR : Your password must contains one digit from 0-9...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one digit from 0-9...",
							"400"));
		}

		if (!passwordEmailVal.EmailValidator(signup.getEmail()))
		{
			logger.info("ERROR : Your email address is invalid....");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your email address is invalid....",
							"400"));
		}

		if (userRepository.existsByEmail(signup.getEmail()))
		{
			logger.info("ERROR : Email is already in use!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Email is already in use!",
							"400"));
		}

		if (!passwordEmailVal.confirmPassword(signup.getPassword(),signUpRequest.getConfirmPassword()))
		{
			logger.info("ERROR : Please check your password not Match!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Please check your password not Match!",
							"400"));
		}

		//parse +62 -> 08
		String va = signup.getNoTelepon().substring(3,signUpRequest.getNoTelepon().length());
		// Create new user's account and encode password
		User user = new User();
		user.setNoTelepon(signup.getNoTelepon());
		user.setEmail(signup.getEmail());
		user.setVirtualAccount("80000"+va);
		user.setNamaUser(signup.getNamaUser());
		user.setPassword(encoder.encode(signup.getPassword()));
		user.setStatus("200");
		user.setMessage("signup is successfully");
		//save to database
		User users = userRepository.save(user);

		EmailVerification confirmationToken = new EmailVerification();
		confirmationTokenRepository.save(confirmationToken);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Test test");
		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
		mailMessage.setText("To confirm "+"http://localhost:6565/api/auth/confirmation-account/"+
				user.getId());
		emailSenderService.sendEmail(mailMessage);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/confirmation-account/{id}")
	public ResponseEntity<?> confirmationUserAccount(@PathVariable("id") Long id)
	{
		 Optional<EmailVerification> token = confirmationTokenRepository.findById(id);

			if (token != null && count < 1)
			{
				count ++;
				System.out.println(count);
				return ResponseEntity.ok(new MessageResponse("Account Verified","200"));

			} else if (count > 1)
			{
				return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Already used...!","400"));
			}
			{
				return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The link is invalid or broken","400"));
			}
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LoginRequest loginRequest) {

		// send to rabbit
		rabbitMqProducer.logouSendRabbit(loginRequest);

		// take from rabbit
		LoginRequest logout = rabbitMqCustomer.logoutRequest(loginRequest);

		User us = userRepository.findByNoTelepon(logout.getNoTelepon());

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				logout.getNoTelepon(), logout.getPassword());

		Authentication authentication = authenticationManager.authenticate(authRequest);

		if (token != null && logout.getPassword() != null && logout.getNoTelepon() != null)
		{

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			token = null;
			JwtResponse jwtResponse = new JwtResponse();
			jwtResponse.setToken(token);
			jwtResponse.setEmail(userDetails.getEmail());
			jwtResponse.setId(userDetails.getId());
			jwtResponse.setUsername(userDetails.getUsername());
			System.out.println("ini token " +token);
			return ResponseEntity.ok((jwtResponse));
		} else {
			return ResponseEntity.ok((new MessageResponse("ERROR : You are not logged in..!, Please login",
					"400")));
		}
	}

	@PostMapping("/update-userpassword")
	public ResponseEntity<?> logoutUser1(@Valid @RequestBody LoginRequest loginRequest) {

		// send to rabbit
		rabbitMqProducer.updateSendRabbit(loginRequest);

		//take from rabbit
		LoginRequest update = rabbitMqCustomer.updateRequest(loginRequest);

		User us = userRepository.findByNoTelepon(update.getNoTelepon());

		UsernamePasswordAuthenticationToken authRequest = new
				UsernamePasswordAuthenticationToken(update.getNoTelepon(), plainPassword);

		Authentication authentication = authenticationManager.authenticate(authRequest);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		if (token != null && update.getPassword() != null && update.getNoTelepon() != null)
		{
			//parse data
			String username =userDetails.getUsername();
			String email =userDetails.getEmail();
			Long id = userDetails.getId();
			String password =loginRequest.getPassword();

			//delete data
			userRepository.deleteById(id);

			//create new data
			User users = new User();
			users.setNoTelepon(username);
			users.setPassword(encoder.encode(password));
			users.setEmail(email);
			users.setId(id);

			//genereate new token
			Authentication newAuth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));

			SecurityContextHolder.getContext().setAuthentication(newAuth);
			token = jwtUtils.generateJwtToken(newAuth);
			return ResponseEntity.ok((users+token));
		}else
		{
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : You are not logged in..!, Please login",
					"400")));
		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword forgotPassword)
	{
		User user = userRepository.findByEmail(forgotPassword.getEmail());

		if (userRepository.existsByEmail(forgotPassword.getEmail()))
		{
			ForgotPassword forgotPasswords = new ForgotPassword();
			forgotPasswords.setEmail(forgotPassword.getEmail());
			forgotPasswordRepository.save(forgotPasswords);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Test test");
			mailMessage.setFrom("setiawan.aanbudi@gmail.com");
			mailMessage.setText("To confirm reset password "+"http://localhost:6565/api/auth/confirm-password/"+
					forgotPasswords.getTokenReset());
			emailSenderService.sendEmail(mailMessage);
			return ResponseEntity.ok(new MessageResponse("Success send Forgot Password","200"));
		}else {
			return ResponseEntity.ok(new MessageResponse("Email not registered...!","400"));
		}

	}

	@GetMapping("confirm-password/{token}")
	public ResponseEntity<?> confirmResetPassword(@PathVariable("token") String token)
	{
		return ResponseEntity.ok(new MessageResponse(token,"ok"));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetUserPassword(@RequestBody User user)
	{
		if (user.getEmail() != null)
		{
			User userToken = userRepository.findByEmail(user.getEmail());
			userToken.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(userToken);
			return ResponseEntity.ok(new MessageResponse("Password successfully reset","200"));
		}
		return ResponseEntity.ok(new MessageResponse("message the link is invalid or broken","400"));
	}

	@PostMapping("/pwd")
	public ResponseEntity<?> test(@RequestBody ForgotPassword forgotPassword)
	{
		User user = userRepository.findByEmail(forgotPassword.getEmail());
		Long id = user.getId();
		String noTelepon = user.getNoTelepon();
		String namaUser = user.getNamaUser();
		String email = user.getEmail();
		String va = user.getVirtualAccount();
		String password = encoder.encode(forgotPassword.getPassword());


		if (userRepository.existsByEmail(forgotPassword.getEmail()))
		{

			//delete data
			userRepository.deleteById(id);

			// new password
			User users = new User();
			users.setId(id);
			users.setNoTelepon(noTelepon);
			users.setNamaUser(namaUser);
			users.setEmail(email);
			users.setVirtualAccount(va);
			users.setPassword(password);
			userRepository.save(users);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Congratulation, your password has been upadated successfully!!");
			mailMessage.setFrom("setiawan.aanbudi@gmail.com");
			mailMessage.setText("Don forget your password again, Thanks");
			emailSenderService.sendEmail(mailMessage);
			return ResponseEntity.ok(user);
		}else {
			return ResponseEntity.ok(new MessageResponse("Email not registered...!","400"));
		}

	}

	private Map<String, SmsOtp> otpData = new HashMap<>();

	private final static String ACCOUNT_SID = "ACb3c4fe3afb030fc4975038ed77135694";
	private final static String ACCOUNT_AUTH_ID = "0daa8a0588e12aa01507690d0ac8fc61";

	static {
		Twilio.init(ACCOUNT_SID,ACCOUNT_AUTH_ID);
	}

	@PostMapping("/send-otp/{mobileNumber}/otp")
	public ResponseEntity<?> sendOtp (@PathVariable ("mobileNumber") String mobileNumber) throws IOException {
		SmsOtp otp = new SmsOtp();
		otp.setMobileNumber(mobileNumber);
		otp.setCodeOtp(smsOtpService.createOtp());
		otp.setExpirytime(System.currentTimeMillis()+18000);
		otpData.put(mobileNumber,otp);

		smsOtpService.sendSMS(mobileNumber, otp.getCodeOtp());

		return ResponseEntity.ok(new MessageResponse("Otp Is Send Succesfully","200"));
	}

	@PostMapping("/confirmation-otp/{mobileNumber}/otp")
	public ResponseEntity<?> verifyOtp (@PathVariable ("mobileNumber") String mobileNumber, @RequestBody
										SmsOtp smsOtp)  {

		if (smsOtp.getCodeOtp() == null || smsOtp.getCodeOtp().trim().length() <= 0)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide otp","400"));
		}

		System.out.println(mobileNumber);

		System.out.println(otpData);
		System.out.println(otpData.containsKey(mobileNumber));
		SmsOtp otp = otpData.get(mobileNumber);
//
//		if (!otpData.containsValue(mobileNumber))
//		{
//			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Mobile number not found", "400"));
//		}

		if (otpData == null)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please Cek", "400"));
		}

//		if (!(otp.getExpirytime() >= System.currentTimeMillis()))
//		{
//			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Otp Expired", "400"));
//		}

		if (!(smsOtp.getCodeOtp().equals(otp.getCodeOtp())))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Invalid Otp","400"));
		}

		return ResponseEntity.ok(new MessageResponse("OTP verified successfully","200"));
	}

//	@RequestMapping(value = "/confirmation-otp/{mobilNumber}/otp", method = RequestMethod.POST)
//	public Message sendSMS(@PathVariable ("mobilNumber") String mobilNumber) {
//
//		return smsOtpService.sendSMS(mobilNumber, smsOtpService.createOtp());
//	}
}
