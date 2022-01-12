package course.spring.restjpa.service;


import course.spring.restjpa.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long userId);
    User findByUsername(String username);
    User create(User user);
    User update(User user);
    User deleteById(Long userId);
    long count();
}
