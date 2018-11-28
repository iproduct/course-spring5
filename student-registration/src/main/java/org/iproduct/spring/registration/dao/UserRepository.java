package org.iproduct.spring.registration.dao;

import org.iproduct.spring.registration.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
