package course.spring.coredemo.service.impl;

import course.spring.coredemo.model.User;
import course.spring.coredemo.service.UserService;
import course.spring.coredemo.util.IdGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static course.spring.coredemo.model.Role.*;

@Service
public class UserServiceInMemory implements UserService {
    public static final List<User> USERS = List.of(
            new User("trayan", "trayan123", "trayan@gmail.com", "Trayan", "ILiev",
                    Set.of(READER, AUTHOR, ADMIN))
    );
    private Map<Long, User> users = new ConcurrentHashMap<>();
    private IdGenerator<Long> idGenerator;

    public static UserServiceInMemory create(IdGenerator<Long> idGen) {
        return new UserServiceInMemory(idGen);
    }

    public UserServiceInMemory(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @PostConstruct
    public void init() {
        USERS.forEach(this::createUser);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        user.setId(idGenerator.getNextId());
        users.put(user.getId(), user);
        return user;
    }
}
