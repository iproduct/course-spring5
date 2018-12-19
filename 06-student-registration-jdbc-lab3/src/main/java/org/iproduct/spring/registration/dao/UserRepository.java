package org.iproduct.spring.registration.dao;

import org.iproduct.spring.registration.model.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
    User insert(@Valid User user);
    User save(User user);
    boolean deleteById(long userId);
    long count();
//    Optional<User> findByUsername(String username);
//    Optional<User> findByEmail(String email);
//    List<User> findByRole(String roles);
}
