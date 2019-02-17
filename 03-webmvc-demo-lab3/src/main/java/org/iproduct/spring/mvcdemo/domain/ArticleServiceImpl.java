package org.iproduct.spring.mvcdemo.domain;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.mvcdemo.dao.ArticleDAO;
import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    private List<Article> articles = new CopyOnWriteArrayList<>(
            Arrays.asList(new Article[] {
            new Article("1111111111111111111111", "New in Spring 5",
                    "New in Spring 5 is ...", "Trayan Iliev"),
            new Article("1111111111111111111111", "Dependency Injection",
                    "DI is ...", "Trayan Iliev"),
            new Article("1111111111111111111111", "Web MVC",
                    "Spring Web MVC is ...", "John Smith"),
            new Article("1111111111111111111111", "Reactor",
                    "Spring Reactor enables functional coding ...", "Trayan Iliev")
    }));

    @Autowired
    private ArticleDAO dao;

    @Override
    public List<Article> getAllArticles() {
        return dao.findAll();
    }

    @Override
    public Article add(Article article) {
        Article result = dao.insert(article);
        log.info("Article created: {}", article);
        return article;
    }
}
