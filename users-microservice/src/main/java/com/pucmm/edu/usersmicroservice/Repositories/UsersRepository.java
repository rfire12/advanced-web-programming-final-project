package com.pucmm.edu.usersmicroservice.Repositories;

import java.util.ArrayList;

import com.pucmm.edu.usersmicroservice.Entities.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);

    public ArrayList<User> findAllByUserType(String userType);
}