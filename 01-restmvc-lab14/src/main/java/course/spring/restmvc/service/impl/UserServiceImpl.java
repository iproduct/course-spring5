package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.UserRepository;
import course.spring.restmvc.entity.User;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public Collection<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with ID:%s not found.", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with Username: '%s' not found.", username)));
    }

    @Override
    public User addUser(User user) {
        user.setId(null);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) {
        User found = getUserById(user.getId());
        user.setModified(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User deleted = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("User with ID:%s not found.", id)));
        userRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getCount() {
        return 0;
    }
}
