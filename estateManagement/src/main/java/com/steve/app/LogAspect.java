package com.steve.app;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.steve.app.security.UserPrincipal;

@Aspect
@Component
@EnableAspectJAutoProxy
public class LogAspect {

	@Before("execution(* com.steve.app.parameter.ParameterService.updateParameter(..)))")
	public void logUpdateParameterBefore(JoinPoint joinPoint) {
		String userName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        userName = "anonymous";
	    }
		
		userName = ((UserPrincipal) authentication.getPrincipal()).getFullName();
		System.out.println(joinPoint.getSignature() + "update by: " + userName);
	}
	
	@Before("execution(* com.steve.app.estate.EstateService.updateEstate(..)))")
	public void logUpdateEstateBefore(JoinPoint joinPoint) {
		String userName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        userName = "anonymous";
	    }
		
		userName = ((UserPrincipal) authentication.getPrincipal()).getFullName();
		System.out.println(joinPoint.getSignature() + "update by: " + userName);
	}
}
