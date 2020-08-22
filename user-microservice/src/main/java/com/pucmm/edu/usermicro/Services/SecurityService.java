package com.pucmm.edu.usermicro.Services;

import com.pucmm.edu.usermicro.Entities.MyRole;
import com.pucmm.edu.usermicro.Entities.MyUser;
import com.pucmm.edu.usermicro.Repositories.RoleRepository;
import com.pucmm.edu.usermicro.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void CreateAdmin() {
        MyRole clientRole = new MyRole();
        clientRole.setRole("ROLE_CLIENT");
        roleRepository.save(clientRole);

        MyRole adminRole = new MyRole();
        clientRole.setRole("ROLE_ADMIN");
        roleRepository.save(adminRole);

        MyRole employeeRole = new MyRole();
        clientRole.setRole("ROLE_EMPLOYEE");
        roleRepository.save(employeeRole);

        MyUser adminUser = new MyUser();
        adminUser.setUsername("admin");
        adminUser.setName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setRoles(new HashSet<>(Arrays.asList(adminRole)));
        adminUser.setEmail("admin@test.com");
        adminUser.setPassword(passwordEncoder.encode("admin"));

        userRepository.save(adminUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByUsername(username);

        Set<GrantedAuthority> roles = new HashSet<>();

        for (MyRole role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }
}
