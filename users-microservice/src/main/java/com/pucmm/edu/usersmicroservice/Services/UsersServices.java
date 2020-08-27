package com.pucmm.edu.usersmicroservice.Services;

import java.util.Arrays;

import com.pucmm.edu.usersmicroservice.Entities.User;
import com.pucmm.edu.usersmicroservice.Repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServices {
    @Autowired
    UsersRepository usersRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public void createAdmin() {
        if (usersRepository.findByUsername("admin") == null) {
            User admin = new User();

            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setEmail("admin@example.com");
            admin.setName("Admin");
            admin.setRole("Admin");

            usersRepository.save(admin);
        }
    }

    public void save(User user) {
        usersRepository.save(user);
    }

    public void update(String username, User user) {
        User entity = usersRepository.findByUsername(username);
        entity.setEmail(user.getEmail());
        entity.setName(user.getName());
        usersRepository.save(entity);
    }
}