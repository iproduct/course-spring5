package course.spring.presenter.impl;

import course.spring.model.Article;
import course.spring.presenter.ArticlePresenter;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.Default;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    @Inject
    public ConsoleArticlePresenter(@Default ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
