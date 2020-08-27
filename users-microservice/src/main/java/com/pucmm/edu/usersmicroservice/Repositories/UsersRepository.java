package com.pucmm.edu.usersmicroservice.Repositories;

import com.pucmm.edu.usersmicroservice.Entities.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
}