package course.ws.blogs.service.impl;

import course.ws.blogs.dao.ArticleRepository;
import course.ws.blogs.dao.UserRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import course.ws.blogs.exception.EntityNotFoundException;
import course.ws.blogs.exception.InvalidEntityDataException;
import course.ws.blogs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepo;
    private UserRepository userRepo;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepo, UserRepository userRepo) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findAllArticles() {
        return articleRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Article findArticleById(Long id) {
        return articleRepo.findById(id).orElseThrow(()->new EntityNotFoundException(
                String.format("Article with ID='%d' not found.", id)
        ));
    }

    @Override
    public Article create(Article article) {
        if(article.getId() != null) {
            throw new InvalidEntityDataException("New Article ID should be NULL.");
        }
        var now = LocalDateTime.now();
        article.setCreated(now);
        article.setModified(now);
        article = articleRepo.save(article);
        User author = userRepo.findById(article.getAuthor().getId()).orElseThrow(()->
                new EntityNotFoundException("Author not found."));
        var articles = author.getArticles();
        articles.add(article); // Don't forget to set the second end of the relation (if needed)!
        return article;
    }

    @Override
    public Article update(Article article) {
        Article old = findArticleById(article.getId());
        article.setCreated(old.getCreated());
        article.setModified(LocalDateTime.now());
        return articleRepo.save(article);
    }

    @Override
    public Article deleteArticleById(Long id) {
        Article deleted = findArticleById(id);
        articleRepo.deleteById(id);
        return deleted;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return articleRepo.count();
    }
}
