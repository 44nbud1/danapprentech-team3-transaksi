package project.akhir.danapprentechteam3.login.controllers;

public class BackUp {

//
//    // ubah password
//    @PostMapping("/update-userpassword")
//    public ResponseEntity<?> logoutUser1(@Valid @RequestBody LoginRequest loginRequest) {
//
//        // send to rabbit
//        rabbitMqProducer.updateSendRabbit(loginRequest);
//
//        //take from rabbit
//        LoginRequest update = rabbitMqCustomer.updateRequest(loginRequest);
//
//        User us = userRepository.findByNoTelepon(update.getNoTelepon());
//
//        UsernamePasswordAuthenticationToken authRequest = new
//                UsernamePasswordAuthenticationToken(update.getNoTelepon(), plainPassword);
//
//        Authentication authentication = authenticationManager.authenticate(authRequest);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        if (token != null && update.getPassword() != null && update.getNoTelepon() != null)
//        {
//            //parse data
//            String username =userDetails.getUsername();
//            String email =userDetails.getEmail();
//            Long id = userDetails.getId();
//            String password =loginRequest.getPassword();
//
//            //delete data
//            userRepository.deleteById(id);
//
//            //create new data
//            User users = new User();
//            users.setNoTelepon(username);
//            users.setPassword(encoder.encode(password));
//            users.setEmail(email);
//            users.setId(id);
//
//            //genereate new token
//            Authentication newAuth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password));
//
//            SecurityContextHolder.getContext().setAuthentication(newAuth);
//            token = jwtUtils.generateJwtToken(newAuth);
//            return ResponseEntity.ok((users+token));
//        }else
//        {
//            return ResponseEntity.badRequest().body((new MessageResponse("ERROR : You are not logged in..!, Please login",
//                    "400")));
//        }
//    }


//	@PostMapping("/reset-password")
//	public ResponseEntity<?> resetUserPassword(@RequestBody User user)
//	{
//		if (!statusTokenForgotPassword)
//		{
//			return ResponseEntity.ok(new MessageResponse("Please update your token!","400"));
//		}
//
//		if (user.getEmail() != null)
//		{
//			User userToken = userRepository.findByEmail(user.getEmail());
//			userToken.setPassword(encoder.encode(user.getPassword()));
//			userRepository.save(userToken);
//			return ResponseEntity.ok(new MessageResponse("Password successfully reset","200"));
//		}
//		return ResponseEntity.ok(new MessageResponse("message the link is invalid or broken","400"));
//	}


//	@PostMapping("/signup-successfully")
//	public ResponseEntity<?> saveUserIfOtpAndEmailVerified()
//	{
//		if (statusVerifyEmail && statusVerifyOtp) {
//			//parse +62 -> 08
//			// Create new user's account and encode password
//			User user = new User();
//			user.setNoTelepon(noTelepon);
//			user.setEmail(email);
//			user.setVirtualAccount(VirtualAccount);
//			user.setNamaUser(namaUser);
//			user.setPassword(password);
//			user.setStatus("200");
//			user.setMessage("signup is successfully");
//			//save to database
//			User users = userRepository.save(user);
//
//			return ResponseEntity.ok(users);
//		}
//		return new ResponseEntity<>(new MessageResponse("Please try again to verify Email and Otp","400"), HttpStatus.BAD_REQUEST);
//	}

//    @PostMapping("/signout1")
//    public ResponseEntity<?> updateUser(@Valid @RequestBody LoginRequest loginRequest)
//    {
//
//        // send to rabbit
//        rabbitMqProducer.logouSendRabbit(loginRequest);
//
//        // take from rabbit
//        LoginRequest logout = rabbitMqCustomer.logoutRequest(loginRequest);
//
//        User us = userRepository.findByNoTelepon(logout.getNoTelepon());
//
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
//                logout.getNoTelepon(), logout.getPassword());
//
//        Authentication authentication = authenticationManager.authenticate(authRequest);
//
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//
//        //parse data
//        String username = userDetails.getUsername();
//        String email = userDetails.getEmail();
//        Long id = userDetails.getId();
//        String password = loginRequest.getPassword();
//
//        //delete data
//        userRepository.deleteById(id);
//
//        //create new data
//        User users = new User();
//        users.setNoTelepon(username);
//        users.setPassword(encoder.encode(password));
//        users.setEmail(email);
//        users.setId(id);
//
//        //genereate new token
//        Authentication newAuth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        SecurityContextHolder.getContext().setAuthentication(newAuth);
//        token = jwtUtils.generateJwtToken(newAuth);
//
//        return ResponseEntity.ok(users);
//
//    }
//
//    @PostMapping("/signout2")
//    public ResponseEntity<?> logoutUser2(@Valid @RequestBody LoginRequest loginRequest) {
//
//        // send to rabbit
//        rabbitMqProducer.logouSendRabbit(loginRequest);
//
//        // take from rabbit
//        LoginRequest logout = rabbitMqCustomer.logoutRequest(loginRequest);
//
//        User us = userRepository.findByNoTelepon(logout.getNoTelepon());
//
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
//                logout.getNoTelepon(), logout.getPassword());
//
//        Authentication authentication = authenticationManager.authenticate(authRequest);
//
//        if (token != null && logout.getPassword() != null && logout.getNoTelepon() != null)
//        {
//
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//            token = null;
//            JwtResponse jwtResponse = new JwtResponse();
//            jwtResponse.setToken(token);
//            jwtResponse.setEmail(userDetails.getEmail());
//            jwtResponse.setId(userDetails.getId());
//            jwtResponse.setUsername(userDetails.getUsername());
//            return ResponseEntity.ok((jwtResponse));
//        } else {
//            return ResponseEntity.ok((new MessageResponse("ERROR : You are not logged in..!, Please login",
//                    "400")));
//        }
//    }
//
//    @GetMapping("confirm")
//    public ResponseEntity<?> confirmResetPassword1()
//    {
//        User user = userRepository.findByEmail("team3.danapprentech@gmail.com");
//        return ResponseEntity.ok(user);
//    }



//	@PostMapping("/signup-successfully")
//	public ResponseEntity<?> saveUserIfOtpAndEmailVerified()
//	{
//		if (statusVerifyEmail && statusVerifyOtp) {
//			//parse +62 -> 08
//			// Create new user's account and encode password
//			User user = new User();
//			user.setNoTelepon(noTelepon);
//			user.setEmail(email);
//			user.setVirtualAccount(VirtualAccount);
//			user.setNamaUser(namaUser);
//			user.setPassword(password);
//			user.setStatus("200");
//			user.setMessage("signup is successfully");
//			//save to database
//			User users = userRepository.save(user);
//
//			return ResponseEntity.ok(users);
//		}
//		return new ResponseEntity<>(new MessageResponse("Please try again to verify Email and Otp","400"), HttpStatus.BAD_REQUEST);
//	}

//    @PostMapping("/signout1")
//    public ResponseEntity<?> updateUser(@Valid @RequestBody LoginRequest loginRequest)
//    {
//
//        // send to rabbit
//        rabbitMqProducer.logouSendRabbit(loginRequest);
//
//        // take from rabbit
//        LoginRequest logout = rabbitMqCustomer.logoutRequest(loginRequest);
//
//        User us = userRepository.findByNoTelepon(logout.getNoTelepon());
//
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
//                logout.getNoTelepon(), logout.getPassword());
//
//        Authentication authentication = authenticationManager.authenticate(authRequest);
//
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//
//        //parse data
//        String username = userDetails.getUsername();
//        String email = userDetails.getEmail();
//        Long id = userDetails.getId();
//        String password = loginRequest.getPassword();
//
//        //delete data
//        userRepository.deleteById(id);
//
//        //create new data
//        User users = new User();
//        users.setNoTelepon(username);
//        users.setPassword(encoder.encode(password));
//        users.setEmail(email);
//        users.setId(id);
//
//        //genereate new token
//        Authentication newAuth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        SecurityContextHolder.getContext().setAuthentication(newAuth);
//        token = jwtUtils.generateJwtToken(newAuth);
//
//        return ResponseEntity.ok(users);
//
//    }
//
//    @PostMapping("/signout2")
//    public ResponseEntity<?> logoutUser2(@Valid @RequestBody LoginRequest loginRequest) {
//
//        // send to rabbit
//        rabbitMqProducer.logouSendRabbit(loginRequest);
//
//        // take from rabbit
//        LoginRequest logout = rabbitMqCustomer.logoutRequest(loginRequest);
//
//        User us = userRepository.findByNoTelepon(logout.getNoTelepon());
//
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
//                logout.getNoTelepon(), logout.getPassword());
//
//        Authentication authentication = authenticationManager.authenticate(authRequest);
//
//        if (token != null && logout.getPassword() != null && logout.getNoTelepon() != null)
//        {
//
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//            token = null;
//            JwtResponse jwtResponse = new JwtResponse();
//            jwtResponse.setToken(token);
//            jwtResponse.setEmail(userDetails.getEmail());
//            jwtResponse.setId(userDetails.getId());
//            jwtResponse.setUsername(userDetails.getUsername());
//            return ResponseEntity.ok((jwtResponse));
//        } else {
//            return ResponseEntity.badRequest().body((new MessageResponse("ERROR : You are not logged in..!, Please login",
//                    "400")));
//        }
//    }
//
//    @GetMapping("confirm")
//    public ResponseEntity<?> confirmResetPassword1()
//    {
//        User user = userRepository.findByEmail("team3.danapprentech@gmail.com");
//        return ResponseEntity.ok(user);
//    }

    public static void main(String[] args) {
        String a = "1234";
                System.out.println(a.length());
    }
}
