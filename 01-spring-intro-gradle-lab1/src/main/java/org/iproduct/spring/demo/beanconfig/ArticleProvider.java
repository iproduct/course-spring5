package org.iproduct.spring.demo.beanconfig;

import org.iproduct.spring.demo.beanconfig.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
}
