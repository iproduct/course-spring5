package org.iproduct.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.PermissonRepository;
import org.iproduct.spring.restmvc.dao.RoleRepository;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.Permission;
import org.iproduct.spring.restmvc.model.Role;
import org.iproduct.spring.restmvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repo;

    @Override
    public List<Role> getRoles() {
        return repo.findAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return repo.findByName(name).orElseThrow(() ->
                new EntityNotFoundException(String.format("Role %s not found.", name)));
    }

    @Override
    public Role createRoleIfNotExist(Role role) {
        Role probe = new Role(role.getName());
        Optional<Role> result = repo.findOne(Example.of(probe));
        if (result.isPresent()) {
            return result.get();
        } else {
            log.info("Inserting new Role: {}", role);
            return repo.insert(role);
        }
    }

    @Override
    public Role updateRole(Role role) {
        Role probe = new Role(role.getName());
        Optional<Role> result = repo.findOne(Example.of(probe));
        if (result.isPresent()) {
            role.setId(result.get().getId());
            return repo.save(role);
        } else {
            log.error("Role does not exist: {}", role);
            throw new InvalidEntityIdException(String.format("Role does not exist: %s", role));
        }
    }

    @Override
    public void deleteRole(Role role) {
        Role probe = new Role(role.getName());
        Optional<Role> result = repo.findOne(Example.of(probe));
        if(result.isPresent()) {
            log.info("Deleting Permission: {}", role);
            repo.delete(result.get());
        } else {
            log.error("Permission does not exist: {}", role);
            throw new InvalidEntityIdException(String.format("Role does not exist: %s", role));
        }
    }
}
