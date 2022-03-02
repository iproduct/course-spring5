package course.ws.blogs.service.impl;

import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.UserService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with ID ='" + id + "' does not exist."));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with username ='" + username + "' does not exist."));
    }

    @Override
    public User create(User user) {
        if(user.getId() != null) {
            throw new InvalidEntityDataException(
                    String.format("User %d: '%s' should not have ID during creation.",
                            user.getId(), user.getUsername()));
        }
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        var now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        User old = getById(user.getId());
        if(!old.getUsername().equals(user.getUsername())) {
            throw new InvalidEntityDataException("Username can not be changed.");
        }
        if(user.getPassword() != null) {
            throw new InvalidEntityDataException("User password can not be changed using 'update' endpoint.");
        }
        user.setPassword(old.getPassword());
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public User deleteById(Long id) {
        User old = getById(id);
        userRepo.deleteById(id);
        return old;
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        return userRepo.count();
    }
}
