package course.spring.myblogsapp.dao;

import course.spring.myblogsapp.entity.Post;
import course.spring.myblogsapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
