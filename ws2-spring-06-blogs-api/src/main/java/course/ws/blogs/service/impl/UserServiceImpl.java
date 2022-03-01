package course.ws.blogs.service.impl;

import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static course.ws.blogs.util.ErrorHandlingUtils.extractExceptionCauseFromClass;

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
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        var now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        try {
            return userRepo.save(user);
        } catch(DataAccessException ex) {
            SQLIntegrityConstraintViolationException icve =
                    extractExceptionCauseFromClass(ex, SQLIntegrityConstraintViolationException.class);
            if(icve != null) {
               throw new InvalidEntityDataException(icve.getMessage());
            }
            throw ex;
        }
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
