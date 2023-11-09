package course.spring.provider.impl;

import course.spring.dao.ArticleRepository;
import course.spring.model.Article;
import course.spring.provider.ArticleProvider;

import java.util.List;
import java.util.Set;

public class RepoArticleProvider implements ArticleProvider {
    public static RepoArticleProvider create(ArticleRepository repository) {
        var provider =  new RepoArticleProvider();
        provider.setRepository(repository);
        return provider;
    }
    private ArticleRepository repository;

    public void setRepository(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Article> getArticles() {
        return repository.findAll();
    }
}
