package course.spring.restmvc.dao;

import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findByTitleInIgnoreCase(Set<String> categoryNames);
}
