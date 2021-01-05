package course.spring.myblogsapp.service;

import course.spring.myblogsapp.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(User user);
    User deleteUserById(Long id);
    long getCount();
}
