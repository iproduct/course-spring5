package course.spring.intro.service;

import course.spring.intro.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String id);
    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(User user);
    User deleteUserById(String id);
    long getCount();
}
