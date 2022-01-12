package course.spring.restjpa.service.impl;


import course.spring.restjpa.dto.UserRepository;
import course.spring.restjpa.entity.User;
import course.spring.restjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> users = userRepo.findAll();
        users.forEach(user -> user.setPassword(""));
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        User found = userRepo.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID=%s not found.", userId)));
        found.setPassword(null);
        return found;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User '%s' not found.", username)));
    }

    @Override
    public User create(User user) {
        user.setId(null);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        User created = userRepo.save(user);
        return created;
    }

    @Override
    public User update(User user) {
        User old = findById(user.getId());
        user.setUsername(old.getUsername());
        if (user.getPassword() == null || user.getPassword().length() == 0) {
            user.setPassword(old.getPassword());
        } else {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        User updated = userRepo.save(user);
        return user;
    }

    @Override
    public User deleteById(Long userId) {
        User old = findById(userId);
        userRepo.deleteById(userId);
        old.setPassword("");
        return old;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return userRepo.count();
    }
}
