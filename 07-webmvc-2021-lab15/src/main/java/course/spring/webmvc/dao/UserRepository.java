package course.spring.webmvc.dao;

import course.spring.webmvc.entity.Article;
import course.spring.webmvc.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
