package com.pucmm.edu.usermicro;

import com.pucmm.edu.usermicro.Services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.event.EventListener;

@EnableEurekaClient
@SpringBootApplication
public class UserMicroApplication {
    @Autowired
    private SecurityService securityService;

    public static void main(String[] args) {
        SpringApplication.run(UserMicroApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void CreateAdminOnStartup() {
        securityService.CreateAdmin();
    }
}
