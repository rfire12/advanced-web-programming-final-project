package com.pucmm.edu.usermicro.Controllers;

import com.pucmm.edu.usermicro.DTO.LoginRequest;
import com.pucmm.edu.usermicro.DTO.LoginResponse;
import com.pucmm.edu.usermicro.Entities.MyRole;
import com.pucmm.edu.usermicro.Entities.MyUser;
import com.pucmm.edu.usermicro.Repositories.UserRepository;
import com.pucmm.edu.usermicro.Services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/")
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Value("ourbesttokenyet")
    private String secret;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/auth")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        String token;
        MyUser user = userRepository.findByUsername(loginRequest.username);
        if (user == null && !user.getPassword().equals(bCryptPasswordEncoder.encode(loginRequest.password))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        token = generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.token = token;
        loginResponse.username = user.getUsername();
        loginResponse.email = user.getEmail();

        for (MyRole role : user.getRoles()) {
            loginResponse.roles.add(role.getRole());
        }

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    private String generateToken(MyUser usuario) {
        String token = Jwts
                .builder()
                .setId("myJWT")
                .setSubject(usuario.getUsername())
                .claim("roles", usuario.getRoles())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secret.getBytes()).compact();

        return token;
    }
}
