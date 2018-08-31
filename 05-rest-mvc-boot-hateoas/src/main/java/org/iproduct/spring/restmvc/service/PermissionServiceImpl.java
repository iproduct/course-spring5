package org.iproduct.spring.restmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.PermissionRepository;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository repo;

    @Override
    public List<Permission> getPermissions() {
        return repo.findAll();
    }

    @Override
    public Permission createPermissionIfNotExist(Permission perm) {
        Permission probe = new Permission(perm.getOwner(), perm.getAsset(), perm.getOperation());
        Optional<Permission> result = repo.findOne(Example.of(probe));
        if(result.isPresent()) {
            return result.get();
        } else {
            log.info("Inserting new Permission: {}", perm);
            return repo.insert(perm);
        }
    }

    @Override
    public Permission updatePermission(Permission permission) {
        return repo.save(permission);
    }

    @Override
    public List<Permission> getPermissionsByAsset(String asset) {
        return repo.findByAsset(asset);
    }

    @Override
    public void deletePermission(Permission  permission) {
        repo.delete(permission);
    }
}
