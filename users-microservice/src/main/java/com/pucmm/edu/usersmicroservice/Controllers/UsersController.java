package com.pucmm.edu.usersmicroservice.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pucmm.edu.usersmicroservice.Entities.User;
import com.pucmm.edu.usersmicroservice.Repositories.UsersRepository;
import com.pucmm.edu.usersmicroservice.Services.UsersServices;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ArrayList<User> employees = usersRepository.findAllByUserType("employee");
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
        String password = DigestUtils.md5Hex(userRequest.getPassword());
        User user = new User(userRequest.getUsername(), userRequest.getName(), password, userRequest.getEmail(),
                userRequest.getUserType());
        usersServices.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping("update")
    public ResponseEntity<String> editarUsuario(@RequestParam String username) {
        usersServices.edit(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}