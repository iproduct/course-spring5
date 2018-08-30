package org.iproduct.spring.restmvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.UserRepository;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.Role;
import org.iproduct.spring.restmvc.model.User;
import org.iproduct.spring.restmvc.service.RoleService;
import org.iproduct.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.iproduct.spring.restmvc.model.Role.ROLE_USER;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleService roleService;

    @Override
    public List<User> getUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(String userId) {
        return repo.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with ID=%s not found.", userId)));
    }

    @Override
    public User getUserByUsername(String username) {
        return repo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User '%s' not found.", username)));

    }

    @Override
    public User createUser(User user) {
        repo.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new InvalidEntityIdException(String.format("User with username '%s' already exists.", user.getUsername()));
        });
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        if(user.getRoles().isEmpty()) {
            user.getRoles().add(roleService.getRoleByName(ROLE_USER));
        } else {
            List<Role> expandedRoles = user.getRoles().stream()
                    .map(role -> roleService.getRoleByName(role.getName()))
                    .collect(Collectors.toList());
            log.debug(">>> Expanded roles: {}", expandedRoles);
            user.setRoles(expandedRoles);
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return repo.save(user);
    }

    @Override
    public User createUserIfNotExist(User user) {
        Optional<User> result = repo.findByUsername(user.getUsername());
        if(result.isPresent()) {
            return result.get();
        } else {
            log.info("Creating User: {}", user);
            return createUser(user);
        }
    }

    @Override
    public User updateUser(User user) {
        User found = repo.findById(user.getId()).orElseThrow(() ->
            new InvalidEntityIdException(String.format("User with ID=%s does not exist.", user.getId()))
        );
        if(!found.getUsername().equals(user.getUsername())) {
            throw new InvalidEntityIdException(String.format("Username '%s' could not be modified to '%s'.",
                    found.getUsername(), user.getUsername()));
        }
        user.setUpdated(LocalDateTime.now());
        return repo.save(user);
    }

    @Override
    public User deleteUser(String userId) {
        User found = repo.findById(userId).orElseThrow(() ->
            new InvalidEntityIdException(String.format("User with ID=%s does not exist.",userId))
        );
        repo.deleteById(userId);
        return found;
    }
}
