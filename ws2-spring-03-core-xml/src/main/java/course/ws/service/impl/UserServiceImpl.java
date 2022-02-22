package course.ws.service.impl;

import course.ws.dao.UserRepository;
import course.ws.exception.EntityNotFoundException;
import course.ws.model.Role;
import course.ws.model.User;
import course.ws.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, InitializingBean {
    @Value("${users.default.admin}")
    private String[] defaultAdminData;
    @Value("${users.default.author}")
    private String[] defaultAuthorData;
    @Value("${users.default.reader}")
    private String[] defaultReaderData;

    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List.of(defaultAdminData, defaultAuthorData, defaultReaderData).stream()
                .map(data -> new User(data[0], data[1], data[2], data[3], Set.of(Role.valueOf(data[4]))))
                .forEach(userRepo::create);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepo.findAll());
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with ID='" + id + "' not found."));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with Username='" + username + "' not found."));
    }

    @Override
    public User createUser(User user) {
        return userRepo.create(user);
    }

}
