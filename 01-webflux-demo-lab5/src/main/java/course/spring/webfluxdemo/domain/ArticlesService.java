package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.model.Article;

import java.util.Collection;
import java.util.List;

public interface ArticlesService {
    Collection<Article> getAll();
    Article add(Article article);
    Article update(Article article);
    Article delete(String articleId);
}
