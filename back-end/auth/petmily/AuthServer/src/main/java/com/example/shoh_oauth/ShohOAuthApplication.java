package com.example.shoh_oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ShohOAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShohOAuthApplication.class, args);
	}
}
