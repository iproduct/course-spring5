package course.spring.coredemo.provider;

import course.spring.coredemo.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getAticles();
}
