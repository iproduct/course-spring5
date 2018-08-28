package org.iproduct.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.PermissonRepository;
import org.iproduct.spring.restmvc.dao.RoleRepository;
import org.iproduct.spring.restmvc.model.Role;
import org.iproduct.spring.restmvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return null;
    }

    @Override
    public Optional<Role> getRoleByName(String asset) {
        return Optional.empty();
    }

    @Override
    public Role createRoleIfNotExist(Role role) {
        return null;
    }

    @Override
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    public void deleteRole(Role role) {

    }
}
