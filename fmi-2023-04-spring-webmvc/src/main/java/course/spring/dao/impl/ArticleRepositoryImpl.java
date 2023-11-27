package course.spring.dao.impl;

import course.spring.dao.ArticleRepository;
import course.spring.dao.IdGenerator;
import course.spring.dao.UserRepository;
import course.spring.model.Article;
import course.spring.model.ArticleCreateDTO;
import course.spring.presenter.ArticlePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("articleRepo")
public class ArticleRepositoryImpl extends RepositoryImpl<Article, Long> implements ArticleRepository {
    private UserRepository userRepo;
    public ArticleRepositoryImpl(IdGenerator<Long> idGen) {
        super(idGen);
    }

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Article create(ArticleCreateDTO createDto) {
        var author = userRepo.findByUsername(createDto.getAuthorUsername());
        var article = new Article(createDto.getTitle(), createDto.getContent(), author.get(), createDto.getKeywords());
        return create(article);
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
