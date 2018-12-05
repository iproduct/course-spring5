package org.iproduct.spring.registration.service;

import org.iproduct.spring.registration.dao.UserRepository;
import org.iproduct.spring.registration.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repo;

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User addUser(User user) {
        return repo.save(user);
    }
}
