package course.spring.myblogsapp.service;

import course.spring.myblogsapp.dao.UserRepository;
import course.spring.myblogsapp.entity.User;
import course.spring.myblogsapp.exception.NonExisitingEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Validated
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(
                () -> new NonExisitingEntityException(
                        String.format("User with ID='%d' does not exists", id)));
    }

    @Override
    @Transactional
    public User addUser(@Valid User user) {
        user.setId(null);
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User updateUser(@Valid User user) {
        User old = getUserById(user.getId());
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {
        User deleted = getUserById(id);
        userRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getUsersCount() {
        return userRepo.count();
    }
}
