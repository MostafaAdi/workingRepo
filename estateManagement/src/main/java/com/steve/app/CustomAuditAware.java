package com.steve.app;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.steve.app.security.UserPrincipal;

public class CustomAuditAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	        return null;
	    }
	    String userName = ((UserPrincipal) authentication.getPrincipal()).getFullName();
	    					
	    return Optional.ofNullable(userName);
	}
	
	

}
