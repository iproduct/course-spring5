package course.spring.restmvc.dao;

import course.spring.restmvc.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
