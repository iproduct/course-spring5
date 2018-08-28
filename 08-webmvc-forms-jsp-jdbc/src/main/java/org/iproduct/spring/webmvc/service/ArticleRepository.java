package org.iproduct.spring.webmvc.service;

        import org.iproduct.spring.webmvc.model.Article;

        import java.util.Collection;
        import java.util.List;

public interface ArticleRepository {
    Collection<Article> findAll();
    Article find(long id);
    Article create(Article article);
    Article update(Article article);
    boolean remove(long articleId);
}
