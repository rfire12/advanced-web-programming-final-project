package usermicro.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usermicro.Services.MyUserService;

@RestController
public class MyUserController {
    @Autowired
    MyUserService userService;

    @RequestMapping("/hello")
    public String hello(HttpServletRequest request) {
        return "HELLO, I'M USER'S MICROSERVICE, RUNNING ON: " + request.getLocalPort();
    }
}