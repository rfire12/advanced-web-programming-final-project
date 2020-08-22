package com.pucmm.edu.usermicro.Repositories;

import com.pucmm.edu.usermicro.Entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUsername(String username);

    MyUser findByEmail(String email);
}
