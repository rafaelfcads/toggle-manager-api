package com.it.toggle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.it.toggle.domain.user.CustomUserDetail;
import com.it.toggle.domain.user.UserRepository;

@SpringBootApplication
public class ToggleManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToggleManagerApiApplication.class, args);
	}
	
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {
		builder.userDetailsService(name -> new CustomUserDetail(repo.findByUserName(name)));
	}
}
