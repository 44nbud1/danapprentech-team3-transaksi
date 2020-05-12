package project.akhir.danapprentechteam3.controllers;

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
//import project.akhir.danapprentechteam3.rabbitmq.rabbitconsumer.RabbitMqConsumer;
//import project.akhir.danapprentechteam3.rabbitmq.rabbitproducer.RabbitMqProducer;
import project.akhir.danapprentechteam3.readdata.service.ProviderValidation;
import project.akhir.danapprentechteam3.repository.ConfirmationTokenRepository;
import project.akhir.danapprentechteam3.repository.ForgotPasswordRepository;
import project.akhir.danapprentechteam3.repository.SmsOtpRepository;
import project.akhir.danapprentechteam3.repository.UserRepository;
import project.akhir.danapprentechteam3.security.jwt.AuthEntryPointJwt;
import project.akhir.danapprentechteam3.security.jwt.JwtUtils;
import project.akhir.danapprentechteam3.security.passwordvalidation.PasswordAndEmailVal;
import project.akhir.danapprentechteam3.security.services.EmailSenderService;
import project.akhir.danapprentechteam3.security.services.SmsOtpServiceImpl;
import project.akhir.danapprentechteam3.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController<ACCOUNT_AUTH_ID, ACCOUNT_SID> {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	private static String token = null;

	private String plainPassword = null;

	// for payload
	String noTelepon;
	String email;
	String VirtualAccount ;
	String namaUser ;
	String password ;

	// otpVerify
	boolean statusVerifyOtp = false;

	// emailVerify
	boolean statusVerifyEmail = false;

	//token forgot password
	boolean statusTokenForgotPassword = false;

	Long count =0L;

	Long countForgotPassword = 0L;

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

//	@Autowired
//	RabbitMqConsumer rabbitMqCustomer;

//	@Autowired/
//	RabbitMqProducer rabbitMqProducer;

	@Autowired
	EmailSenderService emailSenderService;

	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	ForgotPasswordRepository forgotPasswordRepository;

	@Autowired
	SmsOtpServiceImpl smsOtpService;

	@Autowired
	SmsOtpRepository smsOtpRepository;

	//Queue
	private static final String signupKey = "signupKey";

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		//send to rabbi
//		rabbitMqProducer.loginSendRabbit(loginRequest);

		//take from rabbit
//		LoginRequest login = rabbitMqCustomer.loginRequest(loginRequest);

		//init password


		plainPassword = loginRequest.getPassword();

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getNoTelepon(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		token = jwtUtils.generateJwtToken(authentication);

		if (token != null)
		{
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : You are Still logged in..!",
					"400")));
		}
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setEmail(userDetails.getEmail());
		jwtResponse.setId(userDetails.getId());
		jwtResponse.setUsername(userDetails.getUsername());
		return ResponseEntity.ok((jwtResponse));
	}

	// to save mobile number and code Otp
	private Map<String, EmailVerification> signUpMap = new HashMap<>();
	private String signupKeyVal = "";

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws IOException, TimeoutException {

		// send data to rabbit mq
//		rabbitMqProducer.signupSendRabbit(signUpRequest);
		logger.info("Send data to rabbit Mq");

		// take data from rabbit mq
//		SignupRequest signup = rabbitMqCustomer.recievedMessage(signUpRequest);

		logger.info("take data from rabbit mq");
//
//		if (!providerValidation.validasiProvider(signup.getNoTelepon()))
//		{
//			logger.info("ERROR : Username is not registered with the service provider!");
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("ERROR : Phone number is not registered with the service provider!",
//							"400"));
//		}

		if (userRepository.existsByNoTelepon(signUpRequest.getNoTelepon())) {
			logger.info("ERROR : Username is already taken!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Phone number is already taken!",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorSpace(signUpRequest.getPassword())) {
			logger.info("ERROR : Your password does not must contains white space...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password does not must contains white space...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorLowercase(signUpRequest.getPassword()))
		{
			logger.info("ERROR : Your password must contains one lowercase characters...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one lowercase characters...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorUpercase(signUpRequest.getPassword()))
		{
			logger.info("ERROR : Your password must contains one uppercase characters...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one uppercase characters...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorSymbol(signUpRequest.getPassword()))
		{
			logger.info("ERROR : Your password must contains one symbol characters...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one symbol characters...",
							"400"));
		}

		if (!passwordEmailVal.PasswordValidatorNumber(signUpRequest.getPassword()))
		{
			logger.info("ERROR : Your password must contains one digit from 0-9...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one digit from 0-9...",
							"400"));
		}

		if (!passwordEmailVal.EmailValidator(signUpRequest.getEmail()))
		{
			logger.info("ERROR : Your email address is invalid....");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your email address is invalid....",
							"400"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail()))
		{
			logger.info("ERROR : Email is already in use!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Email is already in use!",
							"400"));
		}

		if (!passwordEmailVal.confirmPassword(signUpRequest.getPassword(),signUpRequest.getConfirmPassword()))
		{
			logger.info("ERROR : Please check your password not Match!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Please check your password not Match!",
							"400"));
		}

		//parse +62 -> 08
		String va = signUpRequest.getNoTelepon().substring(3,signUpRequest.getNoTelepon().length());
		// Create new user's account and encode password
		User user = new User();
		user.setNoTelepon(signUpRequest.getNoTelepon());
		user.setEmail(signUpRequest.getEmail());
		user.setVirtualAccount("80000"+va);
		user.setNamaUser(signUpRequest.getNamaUser());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setStatus("200");
		user.setMessage("signup is successfully");

		//save to database
//		User users = userRepository.save(user);

		noTelepon = signUpRequest.getNoTelepon();
		email = signUpRequest.getEmail();
		VirtualAccount = "80000"+va;
		namaUser = signUpRequest.getNamaUser();
		password = encoder.encode(signUpRequest.getPassword());
		//if false detele token if ever ask verify
		confirmationTokenRepository.deleteByConfirmationToken(token);

		// email verify
		signupKeyVal = signUpRequest.getNoTelepon();
		EmailVerification confirmationToken = new EmailVerification();
		confirmationToken.setStatusEmail(true);
		confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
		confirmationToken.setCreatedDate(new Date());
		this.signUpMap.put(signupKeyVal,confirmationToken);
//
		confirmationTokenRepository.save(confirmationToken);

		// email verify
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(signUpRequest.getEmail());
		mailMessage.setSubject("Test test");
		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
		mailMessage.setText("To confirm "+"https://testing-connection-coba.herokuapp.com/api/auth/confirmation-account/"+
				signUpMap.get(signupKeyVal).getConfirmationToken());
		emailSenderService.sendEmail(mailMessage);

		SmsOtp otp = new SmsOtp();
		otp.setMobileNumber(signUpRequest.getNoTelepon());
//		otp.setMobileNumber("+6285777488828");// dummy
		otp.setCodeOtp(smsOtpService.createOtp());
//		otp.setCodeOtp("0657"); // dummy
		smsOtpRepository.save(otp);
//
//		smsOtpService.sendSMS(signUpRequest.getNoTelepon(), otp.getCodeOtp());

		statusVerifyEmail = true;
		return ResponseEntity.ok(new MessageResponse("your otp "+otp.getCodeOtp(),"200"));
	}

	@GetMapping("/confirmation-account/{token}")
	public ResponseEntity<?> confirmationUserAccount(@PathVariable("token")String token)
	{
		EmailVerification tokens = confirmationTokenRepository.findByConfirmationToken(token);

		if (!(token.equalsIgnoreCase(tokens.getConfirmationToken())))
		{
			return ResponseEntity.ok(new MessageResponse("Token Not Found","404"));
		}

//		if (token != null && count < 1)
//		{
			count ++;
			SmsOtp otp = smsOtpRepository.findByCodeOtp(token);

			//parse +62 -> 08
			// Create new user's account and encode password
			User user = new User();
			user.setNoTelepon(noTelepon);
			user.setEmail(email);
			user.setVirtualAccount(VirtualAccount);
			user.setNamaUser(namaUser);
			user.setPassword(password);
			user.setStatus("200");
			user.setMessage("signup is successfully");

			userRepository.save(user);

			return ResponseEntity.ok(userRepository.save(user));

//		} else
//		{
//			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The link is invalid or broken","400"));
//		}
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LoginRequest loginRequest) {

		// send to rabbit
//		rabbitMqProducer.logouSendRabbit(loginRequest);

		// take from rabbit
//		LoginRequest logout = rabbitMqCustomer.logoutRequest(loginRequest);

		User us = userRepository.findByNoTelepon(loginRequest.getNoTelepon());

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				loginRequest.getNoTelepon(), loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(authRequest);

		if (token != null && loginRequest.getPassword() != null && loginRequest.getNoTelepon() != null)
		{

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			token = null;
			JwtResponse jwtResponse = new JwtResponse();
			jwtResponse.setToken(token);
			jwtResponse.setEmail(userDetails.getEmail());
			jwtResponse.setId(userDetails.getId());
			jwtResponse.setUsername(userDetails.getUsername());
			return ResponseEntity.ok((jwtResponse));
		} else {
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : You are not logged in..!, Please login",
					"400")));
		}
	}

	//password
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword forgotPassword)
	{
		User user = userRepository.findByEmail(forgotPassword.getEmail());

		if (userRepository.existsByEmail(forgotPassword.getEmail()))
		{
			ForgotPassword forgotPasswords = new ForgotPassword();
			forgotPasswords.setEmail(forgotPassword.getEmail());
			forgotPasswordRepository.save(forgotPasswords);

			//send email
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Test test");
			mailMessage.setFrom("setiawan.aanbudi@gmail.com");
			mailMessage.setText("To confirm reset password "+"https://testing-connection-coba.herokuapp.com/api/auth/confirm-password/"+
					forgotPasswords.getToken());
			emailSenderService.sendEmail(mailMessage);

			//send phone otp
			SmsOtp otp = new SmsOtp();
			otp.setMobileNumber(forgotPassword.getNoTelepon());
//		otp.setMobileNumber("+6285777488828");// dummy
			otp.setCodeOtp(smsOtpService.createOtp());
//		otp.setCodeOtp("0657"); // dummy
			smsOtpRepository.save(otp);
//			smsOtpService.sendSMS(forgotPassword.getNoTelepon(), otp.getCodeOtp());
			return ResponseEntity.ok(new MessageResponse("your otp "+otp.getCodeOtp(),"200"));
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Email not registered...!","400"));
		}
	}

	@GetMapping("confirm-password/{token}")
	public ResponseEntity<?> confirmResetPassword(@PathVariable("token") String token)
	{
		statusTokenForgotPassword = true;
		return ResponseEntity.badRequest().body(new MessageResponse("Your token has been reset...","200"));
	}

	// email
	@PutMapping("/change-password")
	public ResponseEntity<?> test(@RequestBody ForgotPassword forgotPassword)
	{
		if (!forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Your Password doesnt match..","400"));
		}

		if (!statusTokenForgotPassword)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Please update your token!","400"));
		}

		User user = userRepository.findByEmail(forgotPassword.getEmail());
		Long id = user.getId();
		String noTelepon = user.getNoTelepon();
		String namaUser = user.getNamaUser();
		String email = user.getEmail();
		String va = user.getVirtualAccount();
		String password = encoder.encode(forgotPassword.getNewPassword());

		if (userRepository.existsByEmail(forgotPassword.getEmail()) && countForgotPassword < 1)
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
			users.setPassword(encoder.encode(password));
			users.setStatus("200");
			users.setMessage("changed password is successfully");
			users.setUpdatedDate(new Date());
			userRepository.save(users);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Congratulation, your password has been upadated successfully!!");
			mailMessage.setFrom("setiawan.aanbudi@gmail.com");
			mailMessage.setText("Don forget your password again, Thanks");
			emailSenderService.sendEmail(mailMessage);

			return ResponseEntity.ok(users);
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Email not registered...!","400"));
		}
	}

	// forgot password by otp
	@PutMapping("/confirmation-forgotPassword/{mobileNumber}/otp")
	public ResponseEntity<?> sendOtp (@PathVariable ("mobileNumber") String mobileNumber,@RequestBody ForgotPassword forgotPassword) throws IOException
	{
		SmsOtp smsotps = smsOtpRepository.findByMobileNumber(mobileNumber);

		if (!forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : ERROR : Your Password doesnt match..","400"));
		}

		if (forgotPassword.getOtp() == null || forgotPassword.getOtp().trim().length() <= 0)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide otp","400"));
		}

		if (smsotps == null)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please Cek", "400"));
		}

		if (!forgotPassword.isStatusOtp())
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Otp Expired", "400"));
		}

//		if (forgotPassword.getOtp() != null && countForgotPassword < 1)
//		{
			countForgotPassword++;
			User user = userRepository.findByNoTelepon(mobileNumber);
			user.setPassword(encoder.encode(forgotPassword.getNewPassword()));
			user.setStatus("200");
			user.setMessage("Password Already updated");
			user.setUpdatedDate(new Date());
			smsotps.setStatusOtp(Boolean.TRUE);
			smsOtpRepository.save(smsotps);
			//save to database
			userRepository.save(user);
			return ResponseEntity.ok(userRepository.save(user));
//		} else {
//			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The Otp is invalid or broken","400"));
//		}

	}

	@PostMapping("/confirmation-otp/{mobileNumber}/otp")
	public ResponseEntity<?> verifyOtp (@PathVariable ("mobileNumber") String mobileNumber, @RequestBody SmsOtp smsOtp)
	{
		SmsOtp otp = smsOtpRepository.findByMobileNumber(mobileNumber);

		if (smsOtp.getCodeOtp() == null || smsOtp.getCodeOtp().trim().length() <= 0)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide otp","400"));
		}

		if (!otp.getMobileNumber().equalsIgnoreCase(mobileNumber))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Mobile number not found", "400"));
		}

		if (otp == null)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please Cek", "400"));
		}

		if (!smsOtp.getStatusOtp())
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Otp Expired", "400"));
		}

		if (smsOtp.getCodeOtp() != null && count < 1) {
			count++;
			//parse +62 -> 08
			// Create new user's account and encode password
			User user = new User();
			user.setNoTelepon(noTelepon);
			user.setEmail(email);
			user.setVirtualAccount(VirtualAccount);
			user.setNamaUser(namaUser);
			user.setPassword(password);
			user.setStatus("200");
			user.setMessage("signup is successfully");
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			otp.setStatusOtp(Boolean.TRUE);
			smsOtpRepository.save(otp);
			//save to database
			userRepository.save(user);
			return ResponseEntity.ok(userRepository.save(user));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The link is invalid or broken","400"));
		}
	}

	@PostMapping("/reset-password-inapplication")
	public ResponseEntity<?> updatePassword (@RequestBody ForgotPassword forgotPassword)
	{

		User user = userRepository.findByNoTelepon(forgotPassword.getNoTelepon());

		if (user.getNoTelepon() == null)
		{
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : Please check your phone number",
					"400")));
		}

		if (user.getEmail() == null)
		{
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : Please check your email number",
					"400")));
		}

		ForgotPassword forgotPasswords = new ForgotPassword();
		forgotPasswords.setEmail(forgotPassword.getEmail());
		forgotPasswordRepository.save(forgotPasswords);

		//send email
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Test test");
		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
		mailMessage.setText("To confirm reset password "+"https://testing-connection-coba.herokuapp.com/api/auth/confirm-password/"+
				forgotPasswords.getToken());
		emailSenderService.sendEmail(mailMessage);

		//send phone otp
		SmsOtp otp = new SmsOtp();
		otp.setMobileNumber(forgotPassword.getNoTelepon());
//		otp.setMobileNumber("+6285777488828");// dummy
		otp.setCodeOtp(smsOtpService.createOtp());
//		otp.setCodeOtp("0657"); // dummy
		smsOtpRepository.save(otp);
//		smsOtpService.sendSMS(forgotPassword.getNoTelepon(), otp.getCodeOtp());

		return ResponseEntity.badRequest().body(new MessageResponse("your otp "+otp.getCodeOtp(),"400"));
	}
}
