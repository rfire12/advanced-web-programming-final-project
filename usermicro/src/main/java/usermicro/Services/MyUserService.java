package usermicro.Services;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usermicro.Entities.MyUser;
import usermicro.Repositories.MyUserRepository;

@Service
public class MyUserService {
    @Autowired
    MyUserRepository userRepository;

    public MyUser findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void createUser(MyUser user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        Optional<MyUser> user = userRepository.findById(id);
        userRepository.delete(user.get());
    }

    @Transactional
    public void updateUser(MyUser user) {
        Optional<MyUser> tmp = userRepository.findById(user.getId());
        tmp.get().setUsername(user.getUsername());
        tmp.get().setEmail(user.getEmail());
        userRepository.save(tmp.get());
    }

    public MyUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}