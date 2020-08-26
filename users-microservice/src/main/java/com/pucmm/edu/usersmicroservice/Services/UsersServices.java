package com.pucmm.edu.usersmicroservice.Services;

import com.pucmm.edu.usersmicroservice.Entities.User;
import com.pucmm.edu.usersmicroservice.Repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServices {
    @Autowired
    UsersRepository usersRepository;

    public void createAdmin() {
        if (usersRepository.findByUsername("admin") == null) {
            User user = new User("admin", "Admin", "admin", "admin@example.com", "admin");
            usersRepository.save(user);
        }
    }

    public void save(User user) {
        usersRepository.save(user);
    }

    public void edit(String username) {
        usersRepository.findByUsername(username);
    }
}