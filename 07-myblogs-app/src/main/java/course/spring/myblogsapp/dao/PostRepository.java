package course.spring.myblogsapp.dao;

import course.spring.myblogsapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
