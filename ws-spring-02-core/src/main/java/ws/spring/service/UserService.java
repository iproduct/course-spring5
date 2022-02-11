package ws.spring.service;

import ws.spring.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getUsers();
}
