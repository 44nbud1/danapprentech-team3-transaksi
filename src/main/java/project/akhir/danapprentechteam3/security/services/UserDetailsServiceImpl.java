package project.akhir.danapprentechteam3.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.akhir.danapprentechteam3.models.User;
import project.akhir.danapprentechteam3.payload.response.MessageResponse;
import project.akhir.danapprentechteam3.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null){
			return (UserDetails) ResponseEntity.ok(new MessageResponse("User Not Found with username: " + username));
		}

		return UserDetailsImpl.build(user);
	}

}
