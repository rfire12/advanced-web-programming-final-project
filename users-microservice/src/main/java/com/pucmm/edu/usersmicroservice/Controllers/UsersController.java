package com.pucmm.edu.usersmicroservice.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.pucmm.edu.usersmicroservice.DTO.LoginRequest;
import com.pucmm.edu.usersmicroservice.DTO.LoginResponse;
import com.pucmm.edu.usersmicroservice.DTO.UserDTO;
import com.pucmm.edu.usersmicroservice.Entities.User;
import com.pucmm.edu.usersmicroservice.Repositories.UsersRepository;
import com.pucmm.edu.usersmicroservice.Services.UsersServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("api")
public class UsersController {
    @Autowired
    UsersServices usersServices;

    @Autowired
    UsersRepository usersRepository;

    @Value("${token_jwt}")
    private String secretKey;

    @PostMapping("auth")
    public ResponseEntity<LoginResponse> auth(@RequestBody LoginRequest request) {
        String token = "";
        User user = usersRepository.findByUsername(request.username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (user == null || !bCryptPasswordEncoder.matches(request.password, user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        token = generarToken(user);

        LoginResponse response = new LoginResponse();
        response.role = user.getRole();
        response.token = token;
        response.username = user.getUsername();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("hello")
    public String hello(HttpServletRequest request) {
        return "USERS, RUNNING ON: " + request.getLocalPort();
    }

    @CrossOrigin
    @GetMapping("employees")
    public List<UserDTO> listEmployees() {
        List<UserDTO> dtos = new ArrayList<>();
        List<User> users = usersRepository.findAllByRole("Employee");
        for (User user : users) {
            UserDTO dto = new UserDTO();
            dto.username = user.getUsername();
            dto.email = user.getEmail();
            dto.role = user.getRole();
            dto.name = user.getName();
            dtos.add(dto);
        }
        return dtos;
    }

    @CrossOrigin
    @GetMapping("clients")
    public List<UserDTO> listClients() {
        List<UserDTO> dtos = new ArrayList<>();
        List<User> users = usersRepository.findAllByRole("Client");
        for (User user : users) {
            UserDTO dto = new UserDTO();
            dto.username = user.getUsername();
            dto.email = user.getEmail();
            dto.role = user.getRole();
            dto.name = user.getName();
            dtos.add(dto);
        }
        return dtos;
    }

    @CrossOrigin
    @GetMapping("search")
    public UserDTO searchByUsername(@RequestParam String username) {
        User user = usersRepository.findByUsername(username);
        UserDTO dto = new UserDTO();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        dto.name = user.getName();
        return dto;
    }

    @CrossOrigin
    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody UserDTO userRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(userRequest.password);

        usersServices
                .save(new User(userRequest.username, userRequest.name, password, userRequest.email, userRequest.role));

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping("update")
    public ResponseEntity<String> editarUsuario(@RequestBody User user, @RequestParam String username) {
        usersServices.update(username, user);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    private String generarToken(User user) {

        String token = Jwts.builder().setId("softtekJWT").setSubject(user.getName()).claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

}