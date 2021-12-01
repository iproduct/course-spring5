package course.spring.webmvc.domain;

import course.spring.webmvc.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(String userId);
    User findByUsername(String username);
    User create(User user);
    User update(User user);
    User deleteById(String userId);
    long count();
}
