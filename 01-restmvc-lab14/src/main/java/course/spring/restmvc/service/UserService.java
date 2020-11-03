package course.spring.restmvc.service;

import course.spring.restmvc.entity.User;

import java.util.Collection;

public interface UserService {
    Collection<User>  getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User addUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
    long getCount();
}
