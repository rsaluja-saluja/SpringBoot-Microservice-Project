package com.example.orderservice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public RequestInterceptor requestTokenBearerInterceptor() {
		
		return new RequestInterceptor() {
			private static final String AUTHORIZATION_HEADER="Authorization";
			private static final String TOKEN_TYPE = "Bearer";
			@Override
			public void apply(RequestTemplate requestTemplate) {
//				 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//				    if (authentication != null && authentication instanceof JwtAuthenticationToken) {
//				        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
//				        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, token.getToken().getTokenValue()));
//				    }
				// TODO Auto-generated method stub
//				JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//				requestTemplate.header("Authorization", "Bearer "+	token.getToken().getTokenValue());
				String token = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
				System.out.println("Token:"+token);
				requestTemplate.header("Authorization", token);
			}
		};
	
//		return requestTemplate -> {
//			JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//			requestTemplate.header("Authorization", "Bearer "+	token.get.getToken().getTokenValue());
//		};
	}
	
}
