package info.knigoed.manager;

import info.knigoed.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    public User getByUsername(String username) {
        User user = new User();
        user.setUsername("mark");
        user.setPassword("qwerty");
        user.setRole(User.Role.admin);
        return user;
    }
}
