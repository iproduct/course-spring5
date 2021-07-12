package course.spring.blogs.service;

import course.spring.blogs.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User addUser(User user);
    long getCount();
}
