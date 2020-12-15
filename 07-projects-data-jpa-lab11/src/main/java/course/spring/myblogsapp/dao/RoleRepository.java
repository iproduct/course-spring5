package course.spring.myblogsapp.dao;

import course.spring.myblogsapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Post, Long> {
}
