package course.spring.core.service.impl;

import course.spring.core.dao.qualifiers.MockProvider;
import course.spring.core.dao.qualifiers.RepoProvider;
import course.spring.core.model.Article;
import course.spring.core.service.ArticleConsumer;
import course.spring.core.service.ArticleProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsoleArticlePresenter implements ArticleConsumer {
//    private ArticleProvider provider;
    private List<ArticleProvider> providers;


//    public ConsoleArticlePresenter(ArticleProvider provider) {
//        this.provider = provider;
//    }

    @Autowired
    public void setProvider( List<ArticleProvider> repos) {
        this.providers = repos;
    }

//    @Override
//    public void consume() {
//        provider.getArticles().forEach(System.out::println);
//    }
    @Override
    public void consume() {
        providers.stream()
                .flatMap(repo -> repo.getArticles().stream())
                .forEach(System.out::println);
    }
}
