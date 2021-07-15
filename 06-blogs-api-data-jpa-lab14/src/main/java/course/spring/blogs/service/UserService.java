package course.spring.blogs.service;

import course.spring.blogs.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User addUser(User user);
    User updateUser(User user);
    User deleteUserById(Long id);
    long getUsersCount();
}
