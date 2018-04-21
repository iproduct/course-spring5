package org.iproduct.spring.programmatic;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

public class MockArticleProvider implements ArticleProvider{
    private String[] articleTitles;

    public MockArticleProvider(String[] articleTitles){
        this.articleTitles = articleTitles;
    }

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(
            new Article(articleTitles[0], "Spring 5 is great beacuse ..."),
            new Article(articleTitles[1], "Should I use DI or lookup ..."),
            new Article(articleTitles[2], "There are several ways to configure Spring beans.")
        );
    }
}
