package course.spring.springcoredemo.dao;

import course.spring.springcoredemo.exception.NonexistingEntityException;
import course.spring.springcoredemo.model.Article;
import course.spring.springcoredemo.qualifiers.AlternativeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Repository("alternativeRepo")
//@AlternativeProvider
public class AlternativeArticleRepository implements ArticlesRepository {
    @Value("${articles.number}")
    private int numArticles;

    @Autowired
    ApplicationContext ctx;

    @Override
    public Collection<Article> findAll() {
//        int numArticles = Integer.parseInt(ctx.getEnvironment().getProperty("articles.number"));
        System.out.println("Number of articles: " + numArticles);
        List<Article> results = new ArrayList<>();
        for(int i = 0; i < numArticles; i++) {
            results.add(ctx.getBean("article", Article.class));
        }
        return results;
    }

    @Override
    public Article create(Article article) {
        return null;
    }

    @Override
    public Article update(Article article) throws NonexistingEntityException {
        return null;
    }

    @Override
    public Article delete(String articleId) throws NonexistingEntityException {
        return null;
    }
}
