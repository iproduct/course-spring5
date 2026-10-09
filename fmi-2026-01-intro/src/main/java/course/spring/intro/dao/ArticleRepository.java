package course.spring.intro.dao;

import course.spring.intro.entity.Article;
import course.spring.intro.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    public List<Article> findByAuthor(String author);
    public List<Article> findByCategoriesIn(Set<Category> categories);
    public List<Article> findByTagsIn(Set<String> tags);
}
