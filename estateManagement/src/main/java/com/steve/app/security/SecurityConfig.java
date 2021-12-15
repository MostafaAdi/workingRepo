package com.steve.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{


	private UserPrincipalService userPrincipalService;
	
	public SecurityConfig(UserPrincipalService userPrincipalService) {
		this.userPrincipalService = userPrincipalService;
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider() ;
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userPrincipalService);
		return daoAuthenticationProvider ; 
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception {
		http 
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/register").permitAll()
			.antMatchers("/estates/**").authenticated()
			.antMatchers("/params/**").authenticated()
			.antMatchers("/publish/**").authenticated()
			.antMatchers("/new").hasAuthority("ADMIN")
			.antMatchers("/edit/**").hasAuthority("ADMIN")
			.antMatchers("/delete/**").hasAuthority("ADMIN")
		.and()
		.formLogin().defaultSuccessUrl("/index")
			.loginPage("/login")
			.permitAll()
		.and()
		.logout()
			.invalidateHttpSession(true)
	        .clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
		.and()
    	.exceptionHandling().accessDeniedPage("/403");
	}
}
