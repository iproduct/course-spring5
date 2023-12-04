package course.spring.service;


import course.spring.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(Long userId);
    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(User user);
    User removeUser(Long userId);
    long getUserCount();
}
