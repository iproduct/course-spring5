package course.hibernate.spring.service;

import course.hibernate.spring.dto.UserDetailDto;
import course.hibernate.spring.entity.User;

import java.util.List;

public interface UserService {
    List<UserDetailDto> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User create(User user);
    User update(User user);
    User deleteById(Long id);
    List<User> createBatch(List<User> users);
    long count();
}
