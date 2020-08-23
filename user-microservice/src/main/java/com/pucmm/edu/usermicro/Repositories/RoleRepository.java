package com.pucmm.edu.usermicro.Repositories;

import com.pucmm.edu.usermicro.Entities.MyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<MyRole, Integer> {
    MyRole findByRole(String role);
}
