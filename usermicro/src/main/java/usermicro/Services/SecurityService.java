package usermicro.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import usermicro.Entities.MyRole;
import usermicro.Entities.MyUser;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    MyUserService userService;

    @Autowired
    MyRoleService roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createAdmin() {
        if (userService.findByUsername("admin") == null) {
            MyRole clientRole = new MyRole();
            clientRole.setName("ROLE_CLIENT");
            roleService.create(clientRole);

            MyRole adminRole = new MyRole();
            adminRole.setName("ROLE_ADMIN");
            roleService.create(adminRole);

            MyRole employeeRole = new MyRole();
            employeeRole.setName("ROLE_EMPLOYEE");
            roleService.create(employeeRole);

            MyUser adminUser = new MyUser();
            adminUser.setUsername("admin");
            adminUser.setName("Admin");
            adminUser.setLastName("Admin");
            adminUser.setRoles(new HashSet<>(Arrays.asList(adminRole)));
            adminUser.setEmail("admin@example.com");

            adminUser.setPassword(bCryptPasswordEncoder.encode("admin"));

            userService.createUser(adminUser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userService.findByUsername(username);
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (MyRole role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }
}