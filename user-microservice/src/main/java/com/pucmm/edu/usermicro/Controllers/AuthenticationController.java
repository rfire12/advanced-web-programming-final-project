package com.pucmm.edu.usermicro.Controllers;

import com.pucmm.edu.usermicro.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;
}
