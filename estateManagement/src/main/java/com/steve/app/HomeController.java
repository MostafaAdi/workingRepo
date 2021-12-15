package com.steve.app;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.steve.app.security.User;
import com.steve.app.security.UserService;

@RestController
public class HomeController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");
		return mav ; 
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
    	ModelAndView mav = new ModelAndView("login");
    	return mav; 
    }
	
	@GetMapping("/register")
	public ModelAndView showRegistrationForm() {
	   ModelAndView mav = new ModelAndView("signup_form");
	   return mav;
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
	    
		userService.addUser(user);  
	    return "register_success";
	}
}
