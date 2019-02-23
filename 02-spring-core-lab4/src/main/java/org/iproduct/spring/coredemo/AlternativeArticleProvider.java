package org.iproduct.spring.coredemo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@Service
//@Qualifier("alternativeProvider")
//@AlternativeProvider
public class AlternativeArticleProvider implements ArticleProvider {
    public static ArticleProvider createInstance(){
        return new AlternativeArticleProvider();
    }

    @Value("${articles.titles}")
    private String[] articleTiles;

    @PostConstruct
    public void init() {
        System.out.println(this);
    }

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(articleTiles).stream()
                .map(title -> new Article(title, title + " content ..."))
                .collect(Collectors.toList());
    }
}
