package course.spring.restmvc.dao;

import course.spring.restmvc.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsJpaRepository extends JpaRepository<Post, Long> {
}
