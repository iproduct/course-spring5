package course.ws.dao;

import course.ws.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article>  findByAuthorContaining(String author);
}
