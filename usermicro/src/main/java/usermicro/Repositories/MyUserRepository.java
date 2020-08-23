package usermicro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import usermicro.Entities.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUsername(String username);
}