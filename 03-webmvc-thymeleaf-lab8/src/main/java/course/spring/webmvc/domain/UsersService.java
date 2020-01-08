package course.spring.webmvc.domain;


import course.spring.webmvc.model.User;

import java.util.List;

public interface UsersService {
    List<User> findAll();
    User findById(String userId);
    User findByUsername(String username);
    User add(User user);
    User update(User user);
    User remove(String userId);
    long count();
}
