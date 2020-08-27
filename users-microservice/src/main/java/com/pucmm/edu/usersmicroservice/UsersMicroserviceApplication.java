package com.pucmm.edu.usersmicroservice;

import java.util.Collections;

import com.pucmm.edu.usersmicroservice.Services.UsersServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class UsersMicroserviceApplication {
	@Autowired
	UsersServices usersServices;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UsersMicroserviceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "0"));
		app.run(args);
	}

	@Bean
	public CommandLineRunner admin() {
		return (args -> {
			usersServices.createAdmin();
		});
	}

}
