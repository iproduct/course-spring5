package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.UserRepository;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.User;
import course.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id).orElseThrow(() ->
            new NonexistingEntityException(String.format("User with ID:%s does not exist.", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new InvalidEntityDataException("Invalid username or password."));
    }

    @Override
    public User addUser(User user) {
        user.setId(null);
        return userRepo.insert(user);
    }

    @Override
    public User updateUser(User user) {
        getUserById(user.getId());
        user.setModified(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public User deleteUser(String id) {
        User removed = getUserById(id);
        userRepo.deleteById(id);
        return removed;
    }

    @Override
    public long getCount() {
        return userRepo.count();
    }
}
