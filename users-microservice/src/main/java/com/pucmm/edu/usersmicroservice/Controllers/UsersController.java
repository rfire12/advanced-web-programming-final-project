package com.pucmm.edu.usersmicroservice.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pucmm.edu.usersmicroservice.Entities.User;
import com.pucmm.edu.usersmicroservice.Repositories.UsersRepository;
import com.pucmm.edu.usersmicroservice.Services.UsersServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class UsersController {
    @Autowired
    UsersServices usersServices;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("hello")
    public String hello(HttpServletRequest request) {
        return "USERS, RUNNING ON: " + request.getLocalPort();
    }

    @CrossOrigin
    @GetMapping("employees")
    public ArrayList<User> listEmployees() {
        List<User> users = usersRepository.findAll();

        ArrayList<User> employees = new ArrayList<>();

        for(User u : users){
            if(u.getRoles().contains("ROLE_EMPLOYEE")){
                employees.add(u);
            }
        }

        return employees;
    }

    @CrossOrigin
    @GetMapping("search")
    public User searchByUsername(@RequestParam String username) {
        User user = usersRepository.findByUsername(username);
        return user;
    }

    @CrossOrigin
    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody User userRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(userRequest.getPassword());

        usersServices.save(new User(userRequest.getUsername(), userRequest.getName(), password, userRequest.getEmail(),
                userRequest.getRoles(), false));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping("update/{username}")
    public ResponseEntity<String> editarUsuario(@RequestBody User user, @RequestParam String username) {
        usersServices.update(username, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}