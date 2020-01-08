package course.spring.webmvc.dao;

import course.spring.webmvc.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
