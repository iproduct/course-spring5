package course.spring.domain;

import course.spring.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User addUser(User user);
    long getCount();
}
