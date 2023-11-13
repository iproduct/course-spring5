package course.spring.presenter.impl;

import course.spring.model.Article;
import course.spring.presenter.ArticlePresenter;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    @Inject
    public ConsoleArticlePresenter(@Qualifier("repoProvider") ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
