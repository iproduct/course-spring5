package course.spring.myblogsapp.dao;

import course.spring.myblogsapp.entity.Company;
import course.spring.myblogsapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
