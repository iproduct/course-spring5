package course.spring.core.dao;

import course.spring.core.model.Article;
import course.spring.core.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findByUsername(String usrname);
}
