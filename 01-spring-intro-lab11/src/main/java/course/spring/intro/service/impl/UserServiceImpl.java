package course.spring.intro.service.impl;

import course.spring.intro.dao.UserRepository;
import course.spring.intro.exception.EntityNotFoundException;
import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.model.User;
import course.spring.intro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("User with ID=%s not found.", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new InvalidEntityDataException("Ivalid username or password."));
    }

    @Override
    public User createUser(User user) {
        user.setId(null);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.insert(user);
    }

    @Override
    public User updateUser(User user) {
        User old = getUserById(user.getId());
        user.setPassword(old.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User deleteUserById(String id) {
        User removed = getUserById(id);
        userRepository.deleteById(id);
        return removed;
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }

}
