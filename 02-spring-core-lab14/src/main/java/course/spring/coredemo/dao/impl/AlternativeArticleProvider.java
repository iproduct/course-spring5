package course.spring.coredemo.dao.impl;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlternativeArticleProvider implements ArticleProvider {
    @Override
    public List<Article> getArticles() {
        return null;
    }
}
