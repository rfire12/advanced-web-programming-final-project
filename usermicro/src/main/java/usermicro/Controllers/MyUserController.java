package usermicro.Controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import usermicro.DTO.LoginRequest;
import usermicro.DTO.LoginResponse;
import usermicro.Entities.MyRole;
import usermicro.Entities.MyUser;
import usermicro.Services.MyUserService;

@RestController
@RequestMapping("/user")
public class MyUserController {
    @Autowired
    MyUserService userService;

    @Value("${token}")
    private String tokenKey;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @RequestMapping("/hello")
    public String hello(HttpServletRequest request) {
        return "HELLO, I'M USER'S MICROSERVICE, RUNNING ON: " + request.getLocalPort();
    }

    @PostMapping("/auth")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        String token;

        MyUser user = userService.findUser(loginRequest.username);

        if (user == null
                || (user != null && !bCryptPasswordEncoder.matches(loginRequest.password, user.getPassword()))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        token = generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.token = token;
        loginResponse.username = user.getUsername();
        loginResponse.email = user.getEmail();
        for (MyRole role : user.getRoles()) {
            loginResponse.roles.add(role.getName());
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    private String generateToken(MyUser user) {
        String token = Jwts.builder().setId("outBestJWT").setSubject(user.getUsername()).claim("roles", user.getRoles())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, tokenKey.getBytes()).compact();
        return token;
    }
}