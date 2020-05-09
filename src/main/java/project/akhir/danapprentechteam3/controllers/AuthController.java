package project.akhir.danapprentechteam3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.akhir.danapprentechteam3.models.User;
import project.akhir.danapprentechteam3.payload.request.LoginRequest;
import project.akhir.danapprentechteam3.payload.request.SignupRequest;
import project.akhir.danapprentechteam3.payload.response.JwtResponse;
import project.akhir.danapprentechteam3.payload.response.MessageResponse;
import project.akhir.danapprentechteam3.repository.UserRepository;
import project.akhir.danapprentechteam3.security.jwt.JwtUtils;
import project.akhir.danapprentechteam3.security.passwordvalidation.PasswordAndEmailVal;
import project.akhir.danapprentechteam3.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

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

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		//init password
		plainPassword = loginRequest.getPassword();

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

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
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Username is already taken!"));
		}
		System.out.println(passwordEmailVal.PasswordValidatorSpace(signUpRequest.getPassword()));
		if (!passwordEmailVal.PasswordValidatorSpace(signUpRequest.getPassword())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password does not must contains white space..."));
		}

		if (!passwordEmailVal.PasswordValidatorLowercase(signUpRequest.getPassword()))
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one lowercase characters..."));
		}

		if (!passwordEmailVal.PasswordValidatorUpercase(signUpRequest.getPassword()))
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one uppercase characters..."));
		}

		if (!passwordEmailVal.PasswordValidatorSymbol(signUpRequest.getPassword()))
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one symbol characters..."));
		}

		if (!passwordEmailVal.PasswordValidatorNumber(signUpRequest.getPassword()))
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your password must contains one digit from 0-9..."));
		}

		if (!passwordEmailVal.EmailValidator(signUpRequest.getEmail()))
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Your email address is invalid...."));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("ERROR : Email is already in use!"));
		}

		// Create new user's account and encode password
		User user = new User();
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		return ResponseEntity.ok(userRepository.save(user));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LoginRequest loginRequest) {

		User us = userRepository.findByUsername(loginRequest.getUsername());
		System.out.println(us.getPassword());

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword());

		Authentication authentication = authenticationManager.authenticate(authRequest);

		if (token != null && loginRequest.getPassword() != null && loginRequest.getUsername() != null)
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
			return ResponseEntity.ok((new MessageResponse("ERROR : You are not logged in..!, Please login")));
		}
	}

	@PostMapping("/update-data")
	public ResponseEntity<?> logoutUser1(@Valid @RequestBody LoginRequest loginRequest) {

		User us = userRepository.findByUsername(loginRequest.getUsername());

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
																loginRequest.getUsername(), plainPassword);

		Authentication authentication = authenticationManager.authenticate(authRequest);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		if (token != null && loginRequest.getPassword() != null && loginRequest.getUsername() != null)
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
			users.setUsername(username);
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
			return ResponseEntity.ok((new MessageResponse("ERROR : You are not logged in..!, Please login")));
		}
	}
}
