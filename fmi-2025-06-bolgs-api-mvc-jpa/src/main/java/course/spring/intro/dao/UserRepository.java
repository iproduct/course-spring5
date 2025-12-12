package course.spring.intro.dao;

import course.spring.intro.entity.Article;
import course.spring.intro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
