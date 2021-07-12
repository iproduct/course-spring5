package course.spring.blogs.service.impl;

import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.entity.User;
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.create(user);
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }
}
