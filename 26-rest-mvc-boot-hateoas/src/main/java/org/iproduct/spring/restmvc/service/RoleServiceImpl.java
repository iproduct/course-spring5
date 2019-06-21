package org.iproduct.spring.restmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.RoleRepository;
import org.iproduct.spring.restmvc.model.Role;
import org.iproduct.spring.restmvc.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository repo;

    @Override
    public List<Role> getRoles() {
        return repo.findAll();
    }

    @Override
    public Role createRoleIfNotExist(Role role) {
        Role probe = new Role(role.getName(),null);
        Optional<Role> result = repo.findOne(Example.of(probe));
        if(result.isPresent()) {
            return result.get();
        } else {
            log.info("Inserting new Role: {}", role);
            return repo.insert(role);
        }
    }

    @Override
    public Role updateRole(Role Role) {
        return repo.save(Role);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public void deleteRole(Role  Role) {
        repo.delete(Role);
    }
}
