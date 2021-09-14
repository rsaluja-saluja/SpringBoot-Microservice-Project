package com.example.orderservice.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@PostConstruct
	public void enableAuthenticationContextOnSpawnedThreads() {
		//Required to make SecurityCOntext available to threads spawned by Resilience4J(For eg to call inventory service)
		System.out.println("AUthentication COntext");
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
		
	}
}
