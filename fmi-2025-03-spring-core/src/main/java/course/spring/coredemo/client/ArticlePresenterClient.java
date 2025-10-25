package course.spring.coredemo.client;

import course.spring.coredemo.service.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class ArticlePresenterClient {
    private ArticleProvider articleProvider;

//    @Autowired
    public void setArticleProvider(ArticleProvider articleProvider) {
        this.articleProvider = articleProvider;
    }

    public void presentArticles() {
        articleProvider.getArticles().forEach(System.out::println);
    }
}
