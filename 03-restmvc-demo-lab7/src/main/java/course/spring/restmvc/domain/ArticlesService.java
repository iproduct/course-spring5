package course.spring.restmvc.domain;

import course.spring.restmvc.model.Article;

import java.util.List;

public interface ArticlesService {
    List<Article> findAll();
    Article findById(String articleId);
    Article add(Article article);
    Article update(Article article);
    Article remove(String articleId);
}
