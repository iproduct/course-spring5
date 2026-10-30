package course.spring.service;

import course.spring.model.User;

import java.util.List;

public interface UsersProvider {
    List<User>  getUsers();
    User addUser(User user);
}
