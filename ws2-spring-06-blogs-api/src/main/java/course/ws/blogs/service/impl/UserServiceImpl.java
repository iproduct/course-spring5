package course.ws.blogs.service.impl;

import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var old = findUserById(user.getId());
        if(!user.getEmail().equals(old.getEmail())){
            throw new InvalidEntityDataException(
                    String.format("User email can not be changed from '%s' to '%s'.",
                            old.getEmail(), user.getEmail()));
        }
        if(user.getPassword() != null){
            throw new InvalidEntityDataException(
                    String.format("User password can not be changed using this endpoint."));
        }
        user.setPassword(old.getPassword());
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public User deleteUserById(Long id) {
        var old = findUserById(id);
        userRepo.deleteById(id);
        return old;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return userRepo.count();
    }
}
