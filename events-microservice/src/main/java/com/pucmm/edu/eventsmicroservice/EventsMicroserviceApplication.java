package com.pucmm.edu.eventsmicroservice;

import com.pucmm.edu.eventsmicroservice.Services.ProductServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class EventsMicroserviceApplication {
	@Autowired
	ProductServices productServices;

	public static void main(String[] args) {
		SpringApplication.run(EventsMicroserviceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner admin() {
		return (args -> {
			productServices.initialProducts();
		});
	}

}
