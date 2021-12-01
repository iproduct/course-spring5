package demos.spring.vehicles.service;


import demos.spring.vehicles.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
    long getUsersCount();
    List<User> createUsersBatch(List<User> users);
}
