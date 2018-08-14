package org.iproduct.spring.demo.beanconfig.impl;

import org.iproduct.spring.demo.beanconfig.Article;
import org.iproduct.spring.demo.beanconfig.ArticleProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@Component("provider")
public class MockArticleProvider implements ArticleProvider {
    private String[] articleTitles;
    public MockArticleProvider(){
    }

    public MockArticleProvider(String[] articleTitles){
        this.articleTitles = articleTitles;
    }

    @Override
    public List<Article> getArticles() {
        return Arrays.asList(articleTitles).stream().map(title -> new Article(title, title + " content"))
                .collect(Collectors.toList());
    }
}
