package usermicro.Services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import usermicro.Entities.MyRole;
import usermicro.Repositories.MyRoleRepository;

@Service
public class MyRoleService {
    @Autowired
    MyRoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public MyRole getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public void create(MyRole role) {
        roleRepository.save(role);
    }
}