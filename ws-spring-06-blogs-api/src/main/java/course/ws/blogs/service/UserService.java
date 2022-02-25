package course.ws.blogs.service;

import course.ws.blogs.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User getByUsername(String username);
    User create(User user);
    User update(User user);
    User deleteById(Long id);
    long getCount();
}
