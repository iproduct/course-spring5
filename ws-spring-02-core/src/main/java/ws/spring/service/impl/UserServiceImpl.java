package ws.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ws.spring.dao.UserRepository;
import ws.spring.model.Role;
import ws.spring.model.User;
import ws.spring.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private UserRepository UserRepository;

    @PostConstruct
    public void init() {
        List.of(
                new User("Default", "Admin", "admin", "admin", Role.ADMIN),
                new User("Default", "Author", "author", "author", Role.AUTHOR),
                new User("Default", "Reader", "reader", "reader", Role.READER)
        ).forEach(UserRepository::create);
    }

    @Autowired
    public void setUserRepository(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Override
    public Collection<User> getUsers() {
        return UserRepository.findAll();
    }
}
