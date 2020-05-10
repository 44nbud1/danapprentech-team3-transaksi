package project.akhir.danapprentechteam3.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
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
import project.akhir.danapprentechteam3.repository.UserRepository;
import project.akhir.danapprentechteam3.security.jwt.AuthEntryPointJwt;
import project.akhir.danapprentechteam3.security.jwt.JwtUtils;
import project.akhir.danapprentechteam3.security.passwordvalidation.PasswordAndEmailVal;
import project.akhir.danapprentechteam3.security.services.EmailSenderService;
import project.akhir.danapprentechteam3.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	private static String token = null;

	private String plainPassword = null;

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

		if (token != null)
		{
			return ResponseEntity.ok(new MessageResponse("Account Verified","200"));
		} else
		{
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : The link is invalid or broken","200"));
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


}
