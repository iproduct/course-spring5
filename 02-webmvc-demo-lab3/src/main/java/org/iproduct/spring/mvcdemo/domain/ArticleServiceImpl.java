package org.iproduct.spring.mvcdemo.domain;

import org.iproduct.spring.mvcdemo.model.Article;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Override
    public List<Article> getAllArticles() {
        return Arrays.asList(new Article[] {
                new Article("1111111111111111111111", "New in Spring 5",
                        "New in Spring 5 is ...", "Trayan Iliev"),
                new Article("1111111111111111111111", "Dependency Injection",
                        "DI is ...", "Trayan Iliev"),
                new Article("1111111111111111111111", "Web MVC",
                        "Spring Web MVC is ...", "John Smith"),
                new Article("1111111111111111111111", "Reactor",
                        "Spring Reactor enables functional coding ...", "Trayan Iliev")
        });
    }
}
