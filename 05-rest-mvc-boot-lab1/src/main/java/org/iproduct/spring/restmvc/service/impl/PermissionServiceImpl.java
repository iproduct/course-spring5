package org.iproduct.spring.restmvc.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.PermissonRepository;
import org.iproduct.spring.restmvc.model.Permission;
import org.iproduct.spring.restmvc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissonRepository repo;

    @Override
    public List<Permission> getPermissions() {
        return repo.findAll();
    }

    @Override
    public List<Permission> getPermissionsByAsset(String asset) {
        return repo.findAllByAsset(asset);
    }

    @Override
    public Permission createPermissionIfNotExist(Permission perm) {
        Permission probe = new Permission(perm.getOwner(), perm.getAsset(), perm.getOperation());
        Optional<Permission> result = repo.findOne(Example.of(probe));
        if (result.isPresent()) {
            return result.get();
        } else {
            log.info("Inserting new Permission: {}", perm);
            return repo.insert(perm);
        }
    }

    @Override
    public void deletePermission(Permission perm) {
        Permission probe = new Permission(perm.getOwner(), perm.getAsset(), perm.getOperation());
        Optional<Permission> result = repo.findOne(Example.of(probe));
        if(result.isPresent()) {
            log.info("Deleting Permission: {}", perm);
            repo.delete(result.get());
        } else {
            log.warn("Permission does not exist: {}", perm);
        }
    }
}
