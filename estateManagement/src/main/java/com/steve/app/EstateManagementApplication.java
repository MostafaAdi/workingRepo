package com.steve.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EstateManagementApplication {

	
	@Bean
	public AuditorAware<String> auditorAware(){
	    return new CustomAuditAware();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EstateManagementApplication.class, args);
	}

}
