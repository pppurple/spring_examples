package com.example.client.oauth2.security.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Client
public class SpringSecurityOauth2ClientExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityOauth2ClientExampleApplication.class, args);
	}
}
