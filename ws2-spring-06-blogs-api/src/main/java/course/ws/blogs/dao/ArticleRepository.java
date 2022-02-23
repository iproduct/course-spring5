package course.ws.blogs.dao;

import course.ws.blogs.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article>  findByAuthorContaining(String author);
}
