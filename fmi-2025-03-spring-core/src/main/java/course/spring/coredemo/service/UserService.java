package course.spring.coredemo.service;

import course.spring.coredemo.model.Article;
import course.spring.coredemo.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User createUser(User user);
}
