package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByUsername(String username);
}
