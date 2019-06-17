package course.spring.webfluxdemo.domain;

        import course.spring.webfluxdemo.exception.NonexistingEntityException;
        import course.spring.webfluxdemo.model.Article;

        import java.util.Collection;
        import java.util.List;

public interface ArticlesService {
    Collection<Article> getAll();
    Article add(Article article);
    Article update(Article article) throws NonexistingEntityException;
    Article delete(String articleId) throws NonexistingEntityException;
}
