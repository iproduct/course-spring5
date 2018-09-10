package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
    User insert(@Valid User user);
    User save(User user);
    Optional<User> deleteById(long userId);
    long count();
}
