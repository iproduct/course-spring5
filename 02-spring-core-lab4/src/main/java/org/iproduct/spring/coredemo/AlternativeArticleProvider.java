package org.iproduct.spring.coredemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

//@Service
//@Qualifier("alternativeProvider")
//@AlternativeProvider
@Slf4j
public class AlternativeArticleProvider implements ArticleProvider {
    public static ArticleProvider createInstance(){
        return new AlternativeArticleProvider();
    }

    @Autowired
    ApplicationContext ctx;

    @Value("${articles.number}")
    private int articlesNumber;

    private List<Article> articles = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        log.info("AlternativeProvider created: " + this);
        for(int i = 0; i < articlesNumber; i++) {
            articles.add(ctx.getBean(Article.class));
        }
    }

    @PreDestroy
    public void destroy() {
        log.info("AlternativeProvider to be destroyed: " + this);
    }

    @Override
    public List<Article> getArticles() {
        return articles;
//        return Arrays.asList(articleTiles).stream()
//                .map(title -> new Article(title, title + " content ..."))
//                .collect(Collectors.toList());
    }
}
