package course.spring.myblogsapp.service;

import course.spring.myblogsapp.dao.UserRepository;
import course.spring.myblogsapp.entity.User;
import course.spring.myblogsapp.exception.InvalidEntityDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
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
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("User with ID=%s not found.", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new InvalidEntityDataException("Ivalid username or password."));
    }

    @Override
    @Transactional
    public User createUser(User user) {
        user.setId(null);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User old = getUserById(user.getId());
        user.setPassword(old.getPassword());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User deleteUserById(Long id) {
        User removed = getUserById(id);
        userRepository.deleteById(id);
        return removed;
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }

}
