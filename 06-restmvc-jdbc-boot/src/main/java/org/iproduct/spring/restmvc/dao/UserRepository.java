package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.User;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Collection<User> findAll();
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
    User insert(@Valid User user);
    User save(User user);
    boolean deleteById(long userId);
    long count();
}
