package course.spring.webmvc.domain.impl;

import course.spring.webmvc.dao.UserRepository;
import course.spring.webmvc.domain.UserService;
import course.spring.webmvc.entity.User;
import course.spring.webmvc.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepo.findAll();
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    @Override
    public User findById(String userId) {
        User found =  userRepo.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID=%s not found.", userId)));
        found.setPassword(null);
        return found;
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findById(username).orElseThrow(() ->
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
        User created = userRepo.insert(user);
        created.setPassword(null);
        return created;
    }

    @Override
    public User update(User user) {
        User old = findById(user.getId());
        user.setUsername(old.getUsername());
        if(user.getPassword() == null || user.getPassword().length() == 0) {
            user.setPassword(old.getPassword());
        } else {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        User updated = userRepo.save(user);
        updated.setPassword(null);
        return user;
    }

    @Override
    public User deleteById(String userId) {
        User old = findById(userId);
        userRepo.deleteById(userId);
        old.setPassword(null);
        return old;
    }

    @Override
    public long count() {
        return userRepo.count();
    }
}
