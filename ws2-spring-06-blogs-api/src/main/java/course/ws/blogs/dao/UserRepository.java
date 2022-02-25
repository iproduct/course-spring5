package course.ws.blogs.dao;

import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
