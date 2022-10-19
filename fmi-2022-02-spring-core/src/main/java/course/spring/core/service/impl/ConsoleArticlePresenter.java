package course.spring.core.service.impl;

import course.spring.core.model.Article;
import course.spring.core.service.ArticleConsumer;
import course.spring.core.service.ArticleProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsoleArticlePresenter implements ArticleConsumer {
    private ArticleProvider provider;

    @Autowired
    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void consume() {
        provider.getArticles().forEach(System.out::println);
    }
}
