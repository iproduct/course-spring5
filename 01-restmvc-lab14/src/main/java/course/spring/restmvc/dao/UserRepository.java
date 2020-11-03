package course.spring.restmvc.dao;

import course.spring.restmvc.entity.Post;
import course.spring.restmvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
