package course.spring.blogs.dao;

import course.spring.blogs.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Collection<User> findAll();
    Optional<User> findById(Long id);
    User create(User user);
    User update(User user);
    User deleteById(Long id);
    long count();
}
