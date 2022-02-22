package course.ws.dao;

import course.ws.model.Article;
import course.ws.model.User;

import java.util.Optional;

public interface UserRepository extends Repository<Long, User>{
    Optional<User> findByUsername(String username);
}
