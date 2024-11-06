package course.spring.domain.impl;

import course.spring.dao.UserRepository;
import course.spring.domain.UserService;
import course.spring.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static course.spring.model.Role.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    @Inject
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostConstruct
    public void init() {
        this.userRepo.create(new User("Ivan", "Petrov", "ivan", "ivan123"));
        this.userRepo.create(new User("John", "Doe", "john", "john123",
                Set.of(READER, AUTHOR)));
        this.userRepo.create(new User("Jane", "Smith", "jane", "jane123",
                Set.of(READER, AUTHOR, ADMIN)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepo.create(user);
    }

    @Override
    public long getCount() {
        return userRepo.count();
    }
}
