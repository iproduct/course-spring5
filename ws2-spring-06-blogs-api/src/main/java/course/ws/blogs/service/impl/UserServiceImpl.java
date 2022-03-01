package course.ws.blogs.service.impl;

import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(()->
                new EntityNotFoundException("User with ID='"+ id + "' does not exist."));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(()->
                new EntityNotFoundException("User with email='"+ email + "' does not exist."));
    }

    @Override
    public User create(User user) {
        if(user.getId() != null) {
            throw new InvalidEntityDataException("User ID should NOT be set when creating new user.");
        }
        var now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User deleteUserById(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return 0;
    }
}
