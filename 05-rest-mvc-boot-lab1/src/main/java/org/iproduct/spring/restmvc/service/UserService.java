package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.User;

import java.util.List;

public interface UserService {
    List<User>  getUsers();
    User getUserById(String userId);
    User getUserByUsername(String username);
    User createUser(User user);
    User createUserIfNotExist(User user);
    User updateUser(User user);
    User deleteUser(String userId);
}
