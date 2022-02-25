package course.ws.blogs.service;

import course.ws.blogs.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUserById(Long id);
    User findUserByEmail(String email);
    User create(User user);
    User update(User user);
    User deleteUserById(Long id);
    long count();
}
