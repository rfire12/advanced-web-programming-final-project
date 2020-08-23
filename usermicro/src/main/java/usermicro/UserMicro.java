package usermicro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.event.EventListener;

import usermicro.Services.SecurityService;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class UserMicro {
    @Autowired
    SecurityService securityService;

    public static void main(String[] args) {
        SpringApplication.run(UserMicro.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initApp() {
        securityService.createAdmin();
    }
}
