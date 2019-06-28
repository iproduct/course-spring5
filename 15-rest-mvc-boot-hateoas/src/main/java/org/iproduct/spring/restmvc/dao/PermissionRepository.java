package org.iproduct.spring.restmvc.dao;

import org.iproduct.spring.restmvc.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {
    List<Permission> findByAsset(String asset);
}
