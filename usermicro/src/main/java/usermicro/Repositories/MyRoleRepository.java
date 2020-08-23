package usermicro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import usermicro.Entities.MyRole;

@Repository
public interface MyRoleRepository extends JpaRepository<MyRole, Integer> {
    MyRole findByName(String name);
}