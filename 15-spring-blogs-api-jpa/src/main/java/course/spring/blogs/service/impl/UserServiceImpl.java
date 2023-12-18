package course.spring.blogs.service.impl;

import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserService default implementation
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * @return list all {@link User} entities
     */
    @Override
//    @PostFilter("filterObject.id == authentication.principal.id or hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * @param id the ID of User to be found
     * @return The User with given ID, if such exists
     * @throws NonexistingEntityException when the User with given ID does not exist
     */
    @Override
    public User getUserById(Long id) throws NonexistingEntityException {
        return userRepo.findById(id).orElseThrow(() -> new NonexistingEntityException(
                String.format("Post with ID='%d' does not exist", id)
        ));
    }

    @Override
    public User getUserByUsername(String username) throws NonexistingEntityException {
        return userRepo.findByUsername(username).orElseThrow(() -> new NonexistingEntityException(
                String.format("Post with username = '%s' does not exist", username)
        ));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public User create(User user) throws InvalidEntityDataException{
        user.setId(null);
        if(userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new InvalidEntityDataException(
                    String.format("User with username '%s' already exists", user.getUsername()));
        }
        var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        var now = LocalDateTime.now();
        user.setCreated(now);
        user.setModified(now);
        return userRepo.save(user);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public User update(User user) throws NonexistingEntityException, InvalidEntityDataException {
        var old = getUserById(user.getId());
        if (!old.getUsername().equals(user.getUsername())) {
            throw new InvalidEntityDataException(
                    String.format("User's username can not be changed from '%s' to '%s'",
                            old.getUsername(), user.getUsername()));
        }
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        return userRepo.save(user);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public User deleteUserById(Long id) throws NonexistingEntityException {
        var old = getUserById(id);
        userRepo.deleteById(id);
        return old;
    }

    /**
     * @return
     */
    @Override
    public long getUsersCount() {
        return userRepo.count();
    }
}
