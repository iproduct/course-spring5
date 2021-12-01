package demos.spring.vehicles.service;


import demos.spring.vehicles.model.User;

import java.util.Collection;
import java.util.List;

public interface AuthService {
    User register(User user);
    User login(String username, String password);
}
