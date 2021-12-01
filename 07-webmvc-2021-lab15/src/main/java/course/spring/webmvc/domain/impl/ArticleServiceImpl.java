package course.spring.webmvc.domain.impl;

import course.spring.webmvc.dao.ArticleRepository;
import course.spring.webmvc.domain.ArticleService;
import course.spring.webmvc.entity.Article;
import course.spring.webmvc.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Override
    public List<Article> findAll() {
        return articleRepo.findAll();
    }

    @Override
    public Article findById(String articleId) {
        return articleRepo.findById(articleId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Article with ID=%s not found.", articleId)));
    }

    @Override
    public Article findByArticlename(String articlename) {
        return articleRepo.findById(articlename).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Article '%s' not found.", articlename)));
    }

    @Override
    public Article create(Article article) {
        article.setId(null);
        article.setCreated(LocalDateTime.now());
        article.setModified(LocalDateTime.now());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return articleRepo.insert(article);
    }

    @Override
    public Article update(Article article) {
        Article old = findById(article.getId());
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepo.save(article);
    }

    @Override
    public Article deleteById(String articleId) {
        Article old = findById(articleId);
        articleRepo.deleteById(articleId);
        return old;
    }

    @Override
    public long count() {
        return articleRepo.count();
    }
}
