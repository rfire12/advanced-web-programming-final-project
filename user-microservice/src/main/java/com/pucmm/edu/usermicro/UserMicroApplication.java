package com.pucmm.edu.usermicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class UserMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMicroApplication.class, args);
    }

}
