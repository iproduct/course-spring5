package course.spring.blogs.service.impl;

import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("User with ID=%d does not exist", id))
        );
    }

    @Override
    public User addUser(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User old = getUserById(user.getId());
        if (user.getPosts() != null) {
            throw new InvalidEntityDataException(String.format("User posts should not be changed here: %s",
                    user.getPosts()));
        }
        user.setPosts(old.getPosts());
        User updated = userRepository.save(user);
        return updated;
    }

    @Override
    public User deleteUserById(Long id) {
        User deleted = getUserById(id);
        userRepository.deleteById(id);
        return deleted;
    }

    @Override
    public long getUsersCount() {
        return userRepository.count();
    }
}
