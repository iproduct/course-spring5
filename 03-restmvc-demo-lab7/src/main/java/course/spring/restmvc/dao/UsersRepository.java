package course.spring.restmvc.dao;

import course.spring.restmvc.model.Article;
import course.spring.restmvc.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
