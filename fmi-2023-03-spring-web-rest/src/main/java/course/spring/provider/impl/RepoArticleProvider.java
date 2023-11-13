package course.spring.provider.impl;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.RepoBacked;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Qualifier("repoProvider")
@RepoBacked
public class RepoArticleProvider implements ArticleProvider {
    public static RepoArticleProvider create(ArticleRepository repository) {
        var provider =  new RepoArticleProvider();
        provider.setRepository(repository);
        return provider;
    }
    private ArticleRepository repository;

    @Inject
    public void setRepository(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Article> getArticles() {
        return repository.findAll();
    }
}
