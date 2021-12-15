package com.steve.app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserPrincipalService implements UserDetailsService{

	private UserRepository userRepository ; 
	
	public UserPrincipalService(UserRepository userRepository ){
		this.userRepository = userRepository ; 
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email) ;
		if(user == null ) {
			throw new UsernameNotFoundException(email);
		}
	 	
		return new UserPrincipal(user);
	}

}
