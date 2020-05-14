package project.akhir.danapprentechteam3.login.controllers;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import project.akhir.danapprentechteam3.login.models.User;
import project.akhir.danapprentechteam3.login.payload.request.*;
import project.akhir.danapprentechteam3.login.repository.*;
import project.akhir.danapprentechteam3.login.payload.response.JwtResponse;
import project.akhir.danapprentechteam3.login.payload.response.MessageResponse;
//import project.akhir.danapprentechteam3.rabbitmq.rabbitconsumer.RabbitMqConsumer;
//import project.akhir.danapprentechteam3.rabbitmq.rabbitproducer.RabbitMqProducer;
import project.akhir.danapprentechteam3.login.readdata.service.ProviderValidation;
//import project.akhir.danapprentechteam3.repository.*;
import project.akhir.danapprentechteam3.login.security.jwt.AuthEntryPointJwt;
import project.akhir.danapprentechteam3.login.security.jwt.JwtUtils;
import project.akhir.danapprentechteam3.login.security.passwordvalidation.PasswordAndEmailVal;
import project.akhir.danapprentechteam3.login.security.services.EmailSenderService;
import project.akhir.danapprentechteam3.login.security.services.SmsOtpServiceImpl;
import project.akhir.danapprentechteam3.login.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Transactional
public class AuthController<ACCOUNT_AUTH_ID, ACCOUNT_SID> {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	private String plainPassword = null;

	// for payload
	String noTelepon;
	String email;
	String VirtualAccount ;
	String namaUser ;
	String password ;
	Long pinTransaksi ;

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

	@Autowired
	EmailVerify emailVerify;
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
		User cekUser = userRepository.findByNoTelepon(loginRequest.getNoTelepon());
		//take from rabbit
//		LoginRequest login = rabbitMqCustomer.loginRequest(loginRequest);
		if (cekUser == null)
		{
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : \n" +
					"You have not registered..!",
					"400")));
		}

		if (cekUser.getTokenAkses() != null)
		{
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : You are Still logged in..!",
					"400")));
		}

		User user = userRepository.findByNoTelepon(loginRequest.getNoTelepon());

		plainPassword = loginRequest.getPassword();

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getNoTelepon(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		user.setTokenAkses(token);
		userRepository.save(user);
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setEmail(userDetails.getEmail());
		jwtResponse.setId(userDetails.getId());
		jwtResponse.setUsername(userDetails.getUsername());
		return ResponseEntity.ok((jwtResponse));
	}

	// to save mobile number and code Otp


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

		if (emailVerify.existsByEmail(signUpRequest.getEmail()) && emailVerify.existsByMobileNumber(signUpRequest.getNoTelepon()))
		{
			System.out.println("ya");
			emailVerify.deleteByMobileNumber(signUpRequest.getNoTelepon());
		}

		if (smsOtpRepository.existsByEmail(signUpRequest.getEmail()) && smsOtpRepository.existsByMobileNumber(signUpRequest.getNoTelepon()))
		{
			System.out.println("tidak");
			smsOtpRepository.deleteByMobileNumber(signUpRequest.getNoTelepon());
		}

		//parse +62 -> 08
		String va = signUpRequest.getNoTelepon().substring(3,signUpRequest.getNoTelepon().length());
		// Create new user's account and encode password
		User user = new User();
		user.setNoTelepon(signUpRequest.getNoTelepon());
		user.setEmail(signUpRequest.getEmail());
		user.setPinTransaksi(signUpRequest.getPinTransaksi());
		user.setVirtualAccount("80000"+va);
		user.setNamaUser(signUpRequest.getNamaUser());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setStatus("200");
		user.setMessage("signup is successfully");

		noTelepon = signUpRequest.getNoTelepon();
		email = signUpRequest.getEmail();
		VirtualAccount = "80000"+va;
		namaUser = signUpRequest.getNamaUser();
		password = encoder.encode(signUpRequest.getPassword());
		pinTransaksi = signUpRequest.getPinTransaksi();
		//if false detele token if ever ask verify
//		confirmationTokenRepository.deleteByConfirmationToken(token);

		// number verify
		SmsOtp otp = new SmsOtp();
		otp.setMobileNumber(signUpRequest.getNoTelepon());
//		otp.setMobileNumber("+6285777488828");// dummy
		otp.setCodeOtp(smsOtpService.createOtp());
//		otp.setCodeOtp("0657"); // dummy
		otp.setStatusOtp(true);
		otp.setEmail(signUpRequest.getEmail());
		smsOtpRepository.save(otp);

		// email verify
		EmailOtp emailOtp = new EmailOtp();
		emailOtp.setEmail(signUpRequest.getEmail());
		emailOtp.setCodeVerify(UUID.randomUUID().toString());
		emailOtp.setStatusEmailVerify(true);
		emailOtp.setMobileNumber(signUpRequest.getNoTelepon());
		emailVerify.save(emailOtp);

		// email verify
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(signUpRequest.getEmail());
		mailMessage.setSubject("Test test");
		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
		mailMessage.setText("To confirm "+"https://testing-connection-coba.herokuapp.com/api/auth/confirmation-account/"+
				emailOtp.getCodeVerify());
		emailSenderService.sendEmail(mailMessage);

//		smsOtpService.sendSMS(signUpRequest.getNoTelepon(), otp.getCodeOtp());

		return ResponseEntity.ok(new MessageResponse(signUpRequest.getNoTelepon() +" ---- "+otp.getCodeOtp()+ " your otp "+otp.getCodeOtp(),"200"));
	}

	@PostMapping("/confirmation-account/{token}")
	public ResponseEntity<?> confirmationUserAccount(@PathVariable("token")String token)
	{
		EmailOtp emailOtp = emailVerify.findByCodeVerify(token);
		SmsOtp smsOtp = smsOtpRepository.findByEmail(emailOtp.getEmail());


		if (emailOtp == null)
		{
			return ResponseEntity.ok(new MessageResponse("Token Not Found","404"));
		}

		if (smsOtp.isStatusOtp() == true && emailOtp.isStatusEmailVerify() == true )
		{
			//parse +62 -> 08
			// Create new user's account and encode password
			User user = new User();
			user.setNoTelepon(noTelepon);
			user.setEmail(email);
			user.setVirtualAccount(VirtualAccount);
			user.setNamaUser(namaUser);
			user.setPassword(password);
			user.setPinTransaksi(pinTransaksi);
			user.setStatus("200");
			user.setTokenAkses(null);
			user.setMessage("signup is successfully");

			emailOtp.setStatusEmailVerify(Boolean.FALSE);
			smsOtp.setStatusOtp(Boolean.FALSE);

			smsOtpRepository.save(smsOtp);
			emailVerify.save(emailOtp);
			return ResponseEntity.ok(userRepository.save(user));

		} else
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The link is invalid or broken","400"));
		}
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LoginRequest loginRequest) {

		User us = userRepository.findByNoTelepon(loginRequest.getNoTelepon());

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				loginRequest.getNoTelepon(), loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(authRequest);

		String token = us.getTokenAkses();

		if (token != null && loginRequest.getPassword() != null && loginRequest.getNoTelepon() != null)
		{

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			us.setTokenAkses(null);
			userRepository.save(us);
			JwtResponse jwtResponse = new JwtResponse();
			jwtResponse.setToken(null);
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
		SmsOtp otpNumber = smsOtpRepository.findByMobileNumber(mobileNumber);
		EmailOtp emailOtp = emailVerify.findByMobileNumber(mobileNumber);

		System.out.println(emailOtp);
		System.out.println(otpNumber);

		if (smsOtp.getCodeOtp() == null || smsOtp.getCodeOtp().trim().length() <= 0)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide otp","400"));
		}

		if (otpNumber.isStatusOtp() && emailOtp.isStatusEmailVerify()) {
			//parse +62 -> 08
			// Create new user's account and encode password
			User user = new User();
			user.setNoTelepon(noTelepon);
			user.setEmail(email);
			user.setVirtualAccount(VirtualAccount);
			user.setNamaUser(namaUser);
			user.setPassword(password);
			user.setStatus("200");
			user.setPinTransaksi(pinTransaksi);
			user.setMessage("signup is successfully");
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			//save to database
			otpNumber.setStatusOtp(Boolean.FALSE);
			emailOtp.setStatusEmailVerify(Boolean.FALSE);

			smsOtpRepository.save(otpNumber);
			emailVerify.save(emailOtp);

			user.setTokenAkses(null);
			userRepository.save(user);
			return ResponseEntity.ok(userRepository.save(user));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The link is invalid or broken","400"));
		}
	}
//
//	@PostMapping("/reset-password-inapplication")
//	public ResponseEntity<?> updatePassword (@RequestBody ForgotPassword forgotPassword)
//	{
//
//		User user = userRepository.findByNoTelepon(forgotPassword.getNoTelepon());
//
//		if (user.getUsername() == null)
//		{
//			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : Please check your phone number",
//					"400")));
//		}
//
//		if (user.getEmail() == null)
//		{
//			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : Please check your email number",
//					"400")));
//		}
//
//		ForgotPassword forgotPasswords = new ForgotPassword();
//		forgotPasswords.setEmail(forgotPassword.getEmail());
//		forgotPasswordRepository.save(forgotPasswords);
//
//		//send email
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setTo(user.getEmail());
//		mailMessage.setSubject("Test test");
//		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
//		mailMessage.setText("To confirm reset password "+"https://testing-connection-coba.herokuapp.com/api/auth/confirm-password/"+
//				forgotPasswords.getToken());
//		emailSenderService.sendEmail(mailMessage);
//
//		//send phone otp
//		SmsOtp otp = new SmsOtp();
//		otp.setMobileNumber(forgotPassword.getNoTelepon());
////		otp.setMobileNumber("+6285777488828");// dummy
//		otp.setCodeOtp(smsOtpService.createOtp());
////		otp.setCodeOtp("0657"); // dummy
//		smsOtpRepository.save(otp);
////		smsOtpService.sendSMS(forgotPassword.getNoTelepon(), otp.getCodeOtp());
//
//		return ResponseEntity.badRequest().body(new MessageResponse("your otp "+otp.getCodeOtp(),"400"));
//	}
}
