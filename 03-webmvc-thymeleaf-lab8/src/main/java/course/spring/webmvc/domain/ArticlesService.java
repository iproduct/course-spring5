package course.spring.webmvc.domain;


import course.spring.webmvc.model.Article;

import java.util.List;

public interface ArticlesService {
    List<Article> findAll();
    Article findById(String articleId);
    Article add(Article article);
    Article update(Article article);
    Article remove(String articleId);
    long count();
}
