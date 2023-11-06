package course.spring.dao.impl;

import course.spring.dao.ArticleRepository;
import course.spring.dao.IdGenerator;
import course.spring.model.Article;
import course.spring.presenter.ArticlePresenter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleRepositoryImpl extends RepositoryImpl<Article, Long> implements ArticleRepository {
    public ArticleRepositoryImpl(IdGenerator<Long> idGen) {
        super(idGen);
    }

    @Override
    public List<Article> findByKeywords(Set<String> keywords) {
        return findAll().stream().filter(art -> {
            var kwds = Set.of(art.getKeywords());
            kwds.retainAll(keywords);
            return !kwds.isEmpty();
        }).collect(Collectors.toList());
    }
}
