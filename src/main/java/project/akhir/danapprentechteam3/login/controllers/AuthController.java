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
import project.akhir.danapprentechteam3.login.models.EmailToken;
import project.akhir.danapprentechteam3.login.models.SmsOtp;
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
import project.akhir.danapprentechteam3.transaksi.model.Transaksi;
import project.akhir.danapprentechteam3.transaksi.repository.TransaksiRepository;

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
	SmsOtpServiceImpl smsOtpService;

	@Autowired
	SmsOtpRepository smsOtpRepository;

	@Autowired
	TransaksiRepository transaksiRepository;

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
		jwtResponse.setSaldo(user.getSaldo());
		jwtResponse.setToken(token);
		jwtResponse.setEmail(userDetails.getEmail());
		jwtResponse.setId(userDetails.getId());
		jwtResponse.setUsername(userDetails.getUsername());
		jwtResponse.setStatus("200");
		jwtResponse.setMessage("successfully");
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

		if (!passwordEmailVal.LengthPhoneNumber(signUpRequest.getNoTelepon()))
		{
			logger.info("ERROR : Length phone number must be less than 15 ...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your phone number must be less than 15 ...",
							"400"));
		}

		if (!passwordEmailVal.LengthUsername(signUpRequest.getNamaUser()))
		{
			logger.info("ERROR : Length nama user must be more than equal 3 character...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Length nama user must be less than 3 character...",
							"400"));
		}

		if (!passwordEmailVal.NumberOnlyValidator(signUpRequest.getNoTelepon()))
		{
			logger.info("ERROR : Your phone number must be all numeric...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your phone number must be all numeric ...",
							"400"));
		}

		if (!passwordEmailVal.Alphabetic(signUpRequest.getNamaUser()))
		{
			logger.info("ERROR : Your phone number must be all Alphabetic...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your Full name must be all Alphabetic ...",
							"400"));
		}
		//LengthandNumeric

		if (!passwordEmailVal.LengthandNumeric(signUpRequest.getPinTransaksi()))
		{
			logger.info("ERROR : Your phone number must be all Alphabetic...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your pin length transaksi must be 6...",
							"400"));
		}

		if (!passwordEmailVal.LengthPin(signUpRequest.getPinTransaksi()))
		{
			logger.info("ERROR : Your phone number must be all Alphabetic...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your pin length transaksi must be 6...",
							"400"));
		}

		if (!passwordEmailVal.LengthPassword(signUpRequest.getPassword()))
		{
			logger.info("ERROR : Your password must be more than 7 and less than 17...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must be more than 7 and less than 17...",
							"400"));
		}

		if (emailVerify.existsByEmail(signUpRequest.getEmail()))
		{
			emailVerify.deleteByMobileNumber(signUpRequest.getNoTelepon());
		}

		if (emailVerify.existsByMobileNumber
				(signUpRequest.getNoTelepon()))
		{
			emailVerify.deleteByMobileNumber(signUpRequest.getNoTelepon());

		}

		if (smsOtpRepository.existsByEmail(signUpRequest.getEmail()))
		{
			smsOtpRepository.deleteByMobileNumber(signUpRequest.getNoTelepon());
		}

		if (smsOtpRepository.existsByMobileNumber(signUpRequest.getNoTelepon()))
		{
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

		// number verify
		SmsOtp otp = new SmsOtp();
		otp.setMobileNumber(signUpRequest.getNoTelepon());
		otp.setCodeOtp(smsOtpService.createOtp());
		otp.setStatusOtp(true);
		otp.setEmail(signUpRequest.getEmail());

		// email verify
		EmailToken emailToken = new EmailToken();
		emailToken.setEmail(signUpRequest.getEmail());
		emailToken.setCodeVerify(UUID.randomUUID().toString());
		emailToken.setStatusEmailVerify(true);
		emailToken.setMobileNumber(signUpRequest.getNoTelepon());

		// email verify
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(signUpRequest.getEmail());
		mailMessage.setSubject("Test test");
		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
		mailMessage.setText("To confirm "+"https://project-danapprentech-3.herokuapp.com/api/auth/confirmation-account/"+
				emailToken.getCodeVerify());
		emailSenderService.sendEmail(mailMessage);

		emailVerify.save(emailToken);
		smsOtpRepository.save(otp);
//		smsOtpService.sendSMS(signUpRequest.getNoTelepon(), otp.getCodeOtp());

		return ResponseEntity.ok(new MessageResponse(signUpRequest.getNoTelepon() +" ---- "+otp.getCodeOtp()+
				" your otp "+otp.getCodeOtp(),"200"));
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
			jwtResponse.setMessage("successfully");
			jwtResponse.setStatus("200");
			return ResponseEntity.ok((jwtResponse));
		} else {
			return ResponseEntity.badRequest().body((new MessageResponse("ERROR : You are not logged in..!, Please login",
					"400")));
		}
	}

	/*
	--------------------------------------------------------------------------------------------------------------------
	 */

	@GetMapping("/confirmation-account/{token}")
	public ResponseEntity<?> confirmationUserAccount(@PathVariable("token")String token)
	{
		EmailToken emailToken = emailVerify.findByCodeVerify(token);
		SmsOtp smsOtp = smsOtpRepository.findByEmail(emailToken.getEmail());

		if (emailToken == null)
		{
			return ResponseEntity.ok(new MessageResponse("Token Not Found","404"));
		}

		if (smsOtp.isStatusOtp() == true && emailToken.isStatusEmailVerify() == true )
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

			emailToken.setStatusEmailVerify(Boolean.FALSE);
			smsOtp.setStatusOtp(Boolean.FALSE);

			smsOtpRepository.save(smsOtp);
			emailVerify.save(emailToken);
			return ResponseEntity.ok(userRepository.save(user));

		} else
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The link is invalid or broken","400"));
		}
	}

	/*
	--------------------------------------------------------------------------------------------------------------------
	 */

	@PostMapping("/confirmation-otp/{mobileNumber}/otp")
	public ResponseEntity<?> verifyOtp (@PathVariable ("mobileNumber") String mobileNumber, @RequestBody SmsOtp smsOtp) {
		SmsOtp otpNumber = smsOtpRepository.findByMobileNumber(mobileNumber);
		EmailToken emailToken = emailVerify.findByMobileNumber(mobileNumber);

		if (mobileNumber == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Your phone number wrong..", "400"));
		}

		if (!(otpNumber.getCodeOtp().equalsIgnoreCase(smsOtp.getCodeOtp()))) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Your otp wrong please try aggin..", "400"));
		}

		if (smsOtp.getCodeOtp().length() != 4) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Your otp length must be 4..", "400"));
		}

		if (otpNumber == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Get your Otp", "400"));
		}

		if (otpNumber == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please sign up", "400"));
		}

		if (smsOtp.getCodeOtp() == null || smsOtp.getCodeOtp().trim().length() <= 0) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide otp", "400"));
		}

		if (smsOtp.getCodeOtp().equalsIgnoreCase("0000"))
		{
			otpNumber.setStatusOtp(true);
			emailToken.setStatusEmailVerify(true);
		}

		if (!(otpNumber.isStatusOtp() && emailToken.isStatusEmailVerify()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse(
					"ERROR : The link is invalid or broken", "400"));
		}

		if (!smsOtp.isStatusOtp())
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Wrong otp","400"));
		}

		if ((smsOtp.getCodeOtp().equalsIgnoreCase(otpNumber.getCodeOtp())))
		{

			String status = "200";
			String message = "signup is successfully";

			//parse +62 -> 08
			// Create new user's account and encode password
			User user = new User();
			user.setNoTelepon(noTelepon);
			user.setEmail(email);
			user.setVirtualAccount(VirtualAccount);
			user.setNamaUser(namaUser);
			user.setPassword(password);
			user.setStatus(status);
			user.setPinTransaksi(pinTransaksi);
			user.setMessage(message);
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			//save to database
			otpNumber.setStatusOtp(Boolean.FALSE);
			emailToken.setStatusEmailVerify(Boolean.FALSE);

			smsOtpRepository.save(otpNumber);
			emailVerify.save(emailToken);

			user.setTokenAkses(null);

			return ResponseEntity.ok(userRepository.save(user));
	} else {
		return ResponseEntity.badRequest().body(new MessageResponse(
				"ERROR : Your otp wrong please try aggin..","400"));
	}
	}

	/*
	--------------------------------------------------------------------------------------------------------------------
	 */

	//Lupa password
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword forgotPassword) {
		if (emailVerify.existsByEmail(forgotPassword.getEmail()))
		{
			emailVerify.deleteByMobileNumber(forgotPassword.getNoTelepon());
		}

		if (emailVerify.existsByMobileNumber
				(forgotPassword.getNoTelepon()))
		{
			emailVerify.deleteByMobileNumber(forgotPassword.getNoTelepon());

		}

		if (smsOtpRepository.existsByEmail(forgotPassword.getEmail()))
		{
			smsOtpRepository.deleteByMobileNumber(forgotPassword.getNoTelepon());
		}

		if (smsOtpRepository.existsByMobileNumber(forgotPassword.getNoTelepon()))
		{
			smsOtpRepository.deleteByMobileNumber(forgotPassword.getNoTelepon());
		}


		// number verify
		SmsOtp otp = new SmsOtp();
		otp.setMobileNumber(forgotPassword.getNoTelepon());
		otp.setCodeOtp(smsOtpService.createOtp());
		otp.setStatusOtp(true);
		otp.setEmail(forgotPassword.getEmail());

		// email verify
		EmailToken emailToken = new EmailToken();
		emailToken.setEmail(forgotPassword.getEmail());
		emailToken.setCodeVerify(UUID.randomUUID().toString());
		emailToken.setStatusEmailVerify(true);
		emailToken.setMobileNumber(forgotPassword.getNoTelepon());

		// email verify
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(forgotPassword.getEmail());
		mailMessage.setSubject("Test test");
		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
		mailMessage.setText("To confirm "+"https://project-danapprentech-3.herokuapp.com/api/auth/confirmation-account/"+
				emailToken.getCodeVerify());
		emailSenderService.sendEmail(mailMessage);

		emailVerify.save(emailToken);
		smsOtpRepository.save(otp);
//		smsOtpService.sendSMS(signUpRequest.getNoTelepon(), otp.getCodeOtp());

		return ResponseEntity.ok(new MessageResponse(forgotPassword.getNoTelepon() +" ---- "+otp.getCodeOtp()+
				" your otp "+otp.getCodeOtp(),"200"));
	}

	@GetMapping("confirm-password/{token}")
	public ResponseEntity<?> confirmResetPassword(@PathVariable("token") String token)
	{
		EmailToken emailToken = emailVerify.findByCodeVerify(token);
		emailToken.setStatusEmailVerify(true);
		emailVerify.save(emailToken);
		return ResponseEntity.ok(new MessageResponse("Your token has been reset...","200"));
	}

	// forgot password confirmation by otp
	@PutMapping("/confirmation-forgotPassword/{mobileNumber}/otp")
	public ResponseEntity<?> sendOtp (@PathVariable ("mobileNumber") String mobileNumber,@RequestBody ForgotPassword forgotPassword) throws IOException
	{
		SmsOtp smsotps = smsOtpRepository.findByMobileNumber(mobileNumber);
		EmailToken emailToken = emailVerify.findByMobileNumber(mobileNumber);

		if (mobileNumber == null)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Check your phone number..","400"));
		}

		if (!forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Your Password doesnt match..","400"));
		}

		if (forgotPassword.getOtp() == null || forgotPassword.getOtp().trim().length() <= 0)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide otp","400"));
		}

		if (!smsotps.isStatusOtp())
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Otp Expired", "400"));
		}

		if (forgotPassword.getOtp().length() != 4)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Your otp length must be 4..","400"));
		}

		if (smsotps == null)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Get your Otp","400"));
		}

		if (smsotps.getCodeOtp() == null || smsotps.getCodeOtp().trim().length() <= 0)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide otp","400"));
		}

		if (!forgotPassword.isStatusOtp())
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Wrong otp","400"));
		}

		if (emailToken.isStatusEmailVerify() && smsotps.isStatusOtp())
		{

			User user = userRepository.findByNoTelepon(mobileNumber);
			user.setPassword(encoder.encode(forgotPassword.getNewPassword()));
			user.setStatus("200");
			user.setMessage("Password Already updated");
			user.setUpdatedDate(new Date());
			user.setPinTransaksi(user.getPinTransaksi());
			smsotps.setStatusOtp(false);
			emailToken.setStatusEmailVerify(false);
			emailVerify.save(emailToken);
			smsOtpRepository.save(smsotps);

			return ResponseEntity.ok(userRepository.save(user));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The Otp is invalid or broken","400"));
		}

	}

	/*
	--------------------------------------------------------------------------------------------------------------------
	 */

	@PostMapping("/reset-password-inapplication")
	public ResponseEntity<?> updatePassword (@RequestBody ForgotPassword forgotPassword)
	{
		if (emailVerify.existsByEmail(forgotPassword.getEmail()))
		{
			emailVerify.deleteByMobileNumber(forgotPassword.getNoTelepon());
		}

		if (emailVerify.existsByMobileNumber
				(forgotPassword.getNoTelepon()))
		{
			emailVerify.deleteByMobileNumber(forgotPassword.getNoTelepon());

		}

		if (smsOtpRepository.existsByEmail(forgotPassword.getEmail()))
		{
			smsOtpRepository.deleteByMobileNumber(forgotPassword.getNoTelepon());
		}

		if (smsOtpRepository.existsByMobileNumber(forgotPassword.getNoTelepon()))
		{
			smsOtpRepository.deleteByMobileNumber(forgotPassword.getNoTelepon());
		}


		// number verify
		SmsOtp otp = new SmsOtp();
		otp.setMobileNumber(forgotPassword.getNoTelepon());
		otp.setCodeOtp(smsOtpService.createOtp());
		otp.setStatusOtp(true);
		otp.setEmail(forgotPassword.getEmail());

		// email verify
		EmailToken emailToken = new EmailToken();
		emailToken.setEmail(forgotPassword.getEmail());
		emailToken.setCodeVerify(UUID.randomUUID().toString());
		emailToken.setStatusEmailVerify(true);
		emailToken.setMobileNumber(forgotPassword.getNoTelepon());

		// email verify
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(forgotPassword.getEmail());
		mailMessage.setSubject("Test test");
		mailMessage.setFrom("setiawan.aanbudi@gmail.com");
		mailMessage.setText("To confirm "+"https://project-danapprentech-3.herokuapp.com/api/auth/confirmation-account/"+
				emailToken.getCodeVerify());
		emailSenderService.sendEmail(mailMessage);

		emailVerify.save(emailToken);
		smsOtpRepository.save(otp);
//		smsOtpService.sendSMS(signUpRequest.getNoTelepon(), otp.getCodeOtp());

		return ResponseEntity.ok(new MessageResponse(forgotPassword.getNoTelepon() +" ---- "+otp.getCodeOtp()+
				" your otp "+otp.getCodeOtp(),"200"));
	}

	/*
        --------------------------------------------------------------------------------------------------------------------
     */

	// email
	@PutMapping("/change-password")
	public ResponseEntity<?> test(@RequestBody ForgotPassword forgotPassword)
	{
		SmsOtp smsotps = smsOtpRepository.findByEmail(forgotPassword.getEmail());
		EmailToken emailToken = emailVerify.findByEmail(forgotPassword.getEmail());

		if (!forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : ERROR : Your Password doesnt match..","400"));
		}

		if (forgotPassword.getEmail() == null || forgotPassword.getEmail().trim().length() <= 0)
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Please provide email","400"));
		}

		if (!emailToken.isStatusEmailVerify())
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : Otp Expired", "400"));
		}

		if (emailToken.isStatusEmailVerify() && smsotps.isStatusOtp())
		{

			User user = userRepository.findByEmail(forgotPassword.getEmail());
			user.setPassword(encoder.encode(forgotPassword.getNewPassword()));
			user.setStatus("200");
			user.setMessage("Password Already updated");
			user.setUpdatedDate(new Date());
			user.setPinTransaksi(user.getPinTransaksi());
			smsotps.setStatusOtp(false);
			emailToken.setStatusEmailVerify(false);
			emailVerify.save(emailToken);
			smsOtpRepository.save(smsotps);
			//save to database
			userRepository.save(user);
			return ResponseEntity.ok(userRepository.save(user));
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("Email not registered...!","400"));
		}
	}

	/*
        --------------------------------------------------------------------------------------------------------------------
     */

	@DeleteMapping("/delete-user/{mobileNumber}")
	public ResponseEntity<?> deleteUser(@PathVariable("mobileNumber") String mobileNumber)
	{
		User user = userRepository.findByNoTelepon(mobileNumber);
		List<Transaksi> transaksi = transaksiRepository.findByNomorTeleponUser(mobileNumber);

		if (user == null)
		{
			return ResponseEntity.badRequest().body(new MessageResponse
					("ERROR : Phone Number not registered...!","400"));
		}

		if (transaksi == null)
		{
			userRepository.deleteByNoTelepon(mobileNumber);
			return ResponseEntity.ok(new MessageResponse
					("Your account has been deleted...!","200"));
		}

		transaksiRepository.deleteByNomorTeleponUser(mobileNumber);
		userRepository.deleteByNoTelepon(mobileNumber);
		return ResponseEntity.ok(new MessageResponse
				("Your account has been deleted...!","200"));
	}

	@PutMapping("/edit-user/{mobileNumber}")
	public ResponseEntity<?> updateUser(@PathVariable("mobileNumber") String mobileNumber, @RequestBody
										EditUser editUser)
	{
		User user = userRepository.findByNoTelepon(mobileNumber);

		if (!passwordEmailVal.EmailValidator(editUser.getEmail()))
		{
			logger.info("ERROR : Your email address is invalid....");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your email address is invalid....",
							"400"));
		}

		if (userRepository.existsByEmail(editUser.getEmail()))
		{
			logger.info("ERROR : Email is already in use!");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Email is already in use!",
							"400"));
		}

		if (!passwordEmailVal.NumberOnlyValidator(editUser.getNoTelepon()))
		{
			logger.info("ERROR : Your phone number must be all numeric...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your phone number must be all numeric ...",
							"400"));
		}

		if (!passwordEmailVal.LengthPhoneNumber(editUser.getNoTelepon()))
		{
			logger.info("ERROR : Length phone number must be less than 15 ...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your phone number must be less than 15 ...",
							"400"));
		}

		if (userRepository.existsByNoTelepon(editUser.getNoTelepon()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse
					("ERROR : Phone Number has been used...!","400"));
		}

		if (userRepository.existsByEmail(editUser.getEmail()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse
					("ERROR : Email has been used...!","400"));
		}

		if (user == null)
		{
			return ResponseEntity.badRequest().body(new MessageResponse
					("ERROR : Phone Number not registered...!","400"));
		}

		if (!passwordEmailVal.LengthUsername(editUser.getNamaUser()))
		{
			logger.info("ERROR : Length nama user must be more than equal 3 character...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Length nama user must be less than 3 character...",
							"400"));
		}

		if (!passwordEmailVal.Alphabetic(editUser.getNamaUser()))
		{
			logger.info("ERROR : Your phone number must be all Alphabetic...");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your Full name must be all Alphabetic ...",
							"400"));
		}

		user.setNamaUser(editUser.getNamaUser());
		user.setNoTelepon(editUser.getNoTelepon());
		user.setEmail(editUser.getEmail());
		user.setMessage("Profile has been changed");
		user.setStatus("200");
		return ResponseEntity.ok(userRepository.save(user));
	}

	@GetMapping("/qa-get-otp/{mobileNumber}")
	public ResponseEntity<?> showOtp(@PathVariable("mobileNumber") String mobileNumber)
	{
		return ResponseEntity.ok(smsOtpRepository.findByMobileNumber(mobileNumber));
	}

	@GetMapping("/qa-get-token/{email}")
	public ResponseEntity<?> showToken(@PathVariable("email") String email)
	{
		return ResponseEntity.ok(emailVerify.findByEmail(email));
	}
}
