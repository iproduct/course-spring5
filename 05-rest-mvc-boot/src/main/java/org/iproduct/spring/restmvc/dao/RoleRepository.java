package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.Permission;
import org.iproduct.spring.restmvc.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
