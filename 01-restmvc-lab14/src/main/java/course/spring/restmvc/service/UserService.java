package course.spring.restmvc.service;

import course.spring.restmvc.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User addUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
    long getCount();
}
