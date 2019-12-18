package course.spring.webmvc.domain;

import course.spring.restmvc.exception.NonexisitngEntityException;
import course.spring.webmvc.dao.ArticlesRepository;
import course.spring.webmvc.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesRepository repo;

    @Override
    public List<Article> findAll() {
        return repo.findAll();
    }

    @Override
    public Article findById(String articleId) {
        return repo.findById(articleId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("Article with ID='%s' does not exist.", articleId)));
    }

    @Override
    public Article add(Article article) {
        return repo.insert(article);
    }

    @Override
    public Article update(Article article) {
        Optional<Article> old = repo.findById(article.getId());
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Article with ID='%s' does not exist.", article.getId()));
        }
        article.setCreated(old.get().getCreated());
        article.setModified(LocalDateTime.now());
        return repo.save(article);
    }

    @Override
    public Article remove(String articleId) {
        Optional<Article> old = repo.findById(articleId);
        log.info("!!!!!! ArticleID = " + articleId);
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Article with ID='%s' does not exist.", articleId));
        }
        repo.deleteById(articleId);
        return old.get();
    }

    @Override
    public long count() {
        return repo.count();
    }
}
