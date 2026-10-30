package course.spring.dao;

import course.spring.model.User;

import java.util.List;

public interface UsersRepository {
    List<User> findAll();
    User create(User user);
}
