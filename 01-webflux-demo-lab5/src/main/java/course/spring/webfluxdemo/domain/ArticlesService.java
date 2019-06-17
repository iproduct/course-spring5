package course.spring.webfluxdemo.domain;

import course.spring.webfluxdemo.model.Article;

import java.util.List;

public interface ArticlesService {
    List<Article> getAll();
    Article add(Article article);
}
