package ws.spring.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ws.spring.dao.ArticleRepository;
import ws.spring.model.Article;
import ws.spring.service.ArticleProvider;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Service("repoArticleProvider")
public class RepositoryArticleProvider implements ArticleProvider {
    private ArticleRepository articleRepository;

//    @PostConstruct
    public void init() {
        List.of(
                new Article(1L, "Spring Data is Awesome", "Spring Data is great beacuse ..."),
                new Article(2L, "Spring Data is Bold", "Should I use Spring Data ..."),
                new Article(3L, "Spring Data is Easy", "There are several ways to configure Spring Data.")
        ).forEach(articleRepository::create);
    }

//    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Collection<Article> getArticles() {
        return articleRepository.findAll();
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        List.of(
//                new Article(1L, "Spring Data is Awesome", "Spring Data is great beacuse ..."),
//                new Article(2L, "Spring Data is Bold", "Should I use Spring Data ..."),
//                new Article(3L, "Spring Data is Easy", "There are several ways to configure Spring Data.")
//        ).forEach(articleRepository::create);
//    }
}
