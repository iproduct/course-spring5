package course.ws.blogs.service.impl;

import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with ID ='" + id + "' does not exist."));
    }

    @Override
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
    public long getCount() {
        return userRepo.count();
    }
}
