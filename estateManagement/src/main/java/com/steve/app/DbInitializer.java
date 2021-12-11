package com.steve.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.steve.app.security.User;
import com.steve.app.security.UserRepository;

@Service
public class DbInitializer implements CommandLineRunner{

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private UserRepository userRepository;
	
	public DbInitializer(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		addUsers();
		System.out.println("users added to system");
		
	}
	
	private void addUsers() {
    	User user = new User();
    	user.setUsername("admin");
    	user.setPassword(passwordEncoder.encode("admin"));
    	user.setActive(true);
    	this.userRepository.save(user);
    	User user2 = new User();
    	user2.setUsername("admin2");
    	user2.setPassword(passwordEncoder.encode("admin2"));
    	user2.setActive(true);
    	this.userRepository.save(user2);
    }
}
