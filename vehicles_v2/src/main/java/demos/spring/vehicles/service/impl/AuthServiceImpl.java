package demos.spring.vehicles.service.impl;


import demos.spring.vehicles.dao.UserRepository;
import demos.spring.vehicles.exception.InvalidEntityException;
import demos.spring.vehicles.model.Role;
import demos.spring.vehicles.model.User;
import demos.spring.vehicles.service.AuthService;
import demos.spring.vehicles.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;

    @Override
    public User register(User user) {
        if(user.getRoles().contains(Role.ADMIN) ) {
            throw new InvalidEntityException("Admins can not self-register.");
        }
        return userService.createUser(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userService.getUserByUsername(username);
        if(user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

}
