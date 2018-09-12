package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getUsers();
    User createUser(@Valid User user);
    User updateUser(User user);
    User getUserById(long id);
    User getUserByUsername(String username);
    User deleteUser(long id);
    List<User> createUsersBatch(List<User> users);
    long getUsersCount();
}
