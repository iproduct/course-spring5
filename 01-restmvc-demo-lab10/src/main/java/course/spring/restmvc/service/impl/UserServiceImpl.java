package course.spring.restmvc.service.impl;

import course.spring.restmvc.dao.UserRepository;
import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.entity.User;
import course.spring.restmvc.service.UserService;
import course.spring.restmvc.util.ExceptionHandlingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Slf4j
@Transactional(propagation=REQUIRED)
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    private Validator validator;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) throws NonexistingEntityException {
        User result = userRepo.findById(id).orElseThrow(
                () -> new NonexistingEntityException(String.format("User with ID:%d does not exist", id)));
        return result;
    }

    @Override
    public User getUserByUsername(String username) throws NonexistingEntityException {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public User createUser(User user) {
        user.setId(null);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = null;
        try {
            result = userRepo.save(user);
        } catch(RuntimeException e) {
            ExceptionHandlingUtils.hanleConstraintViolationException(e);
        }
        return result;
    }

    @Override
    public User updateUser(User user) throws InvalidEntityDataException, NonexistingEntityException {
        User result = userRepo.findById(user.getId()).orElseThrow(
                ()->new NonexistingEntityException(String.format("User with ID:%d does not exist", user.getId())));
        if(!result.getCreated().equals(user.getCreated())) {
            throw new InvalidEntityDataException(String.format("User creation date: %s can not be modified.",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(result.getCreated())));
        }
        user.setModified(LocalDateTime.now());
        return userRepo.save(user);
    }

    @Override
    public User deleteUser(Long id) throws NonexistingEntityException{
        User result = getUserById(id);
        userRepo.deleteById(id);
        return result;
    }

    @Override
    public long count() {
        return userRepo.count();
    }


}
