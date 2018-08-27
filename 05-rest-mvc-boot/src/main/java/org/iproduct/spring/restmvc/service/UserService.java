package org.iproduct.spring.restmvc.service;

import org.iproduct.spring.restmvc.model.Role;
import org.iproduct.spring.restmvc.model.User;

import java.util.List;

public interface UserService {
    List<User>  getUsers();
    User createUser(User user);
    User createUserIfNotExist(User user);
    User updateUser(User user);
    User getUserById(String id);
    User getUserByUsername(String username);
    User deleteUser(String id);
}
