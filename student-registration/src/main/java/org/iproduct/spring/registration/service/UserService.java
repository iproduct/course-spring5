package org.iproduct.spring.registration.service;

import org.iproduct.spring.registration.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User addUser(User user);
    long getUsersCount();
}
