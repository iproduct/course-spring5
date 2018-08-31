package org.iproduct.spring.restmvc.service;

import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.events.ArticleCreationEvent;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.exception.InvalidEntityIdException;
import org.iproduct.spring.restmvc.model.Article;
import org.iproduct.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@Slf4j
@Validated
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository repo;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    // single TransactionTemplate shared amongst all methods in this instance
    private final PlatformTransactionManager transactionManager;

    // single TransactionTemplate shared amongst all methods in this instance
    private final TransactionTemplate transactionTemplate;

    // use constructor-injection to supply the PlatformTransactionManager
    public ArticleServiceImpl(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public Collection<Article> getArticles() {
        return repo.findAll();
    }

    @Override
    public Article addArticle(@Valid Article article) {
        article.setCreated(new Date());
        article.setUpdated(new Date());
        return repo.insert(article);

    }

    @Override
    public Article updateArticle(Article article) {
        article.setUpdated(new Date());
        Article old = getArticleById(article.getId());
        if(article.getAuthor() != null && article.getAuthor().getId() != old.getAuthor().getId())
            throw new InvalidEntityIdException("Author of article could not ne changed");
        article.setAuthor(old.getAuthor());
        return repo.save(article);
    }

    @Override
    public Article getArticleById(long id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
    }

    @Override
    public Article deleteArticle(long id) {
        Article old = repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Article with ID=%s not found.", id)));
        repo.deleteById(id);
        return old;
    }

    @Override
    public long getArticlesCount() {
        return repo.count();
    }

    // Declarative transaction
    @Transactional
    public List<Article> createArticlesBatch(List<Article> articles) {
        List<Article> created = articles.stream()
                .map(article -> {
                    Article resultArticle = addArticle(article);
                    applicationEventPublisher.publishEvent(new ArticleCreationEvent(resultArticle));
                    return resultArticle;
                }).collect(Collectors.toList());
        return created;
    }

////    Programmatic transaction
//    public List<Article> createArticlesBatch(List<Article> articles) {
//        return transactionTemplate.execute(new TransactionCallback<List<Article>>() {
//            // the code in this method executes in a transactional context
//            public List<Article> doInTransaction(TransactionStatus status) {
//                List<Article> created = articles.stream()
//                        .map(article -> {
//                            try {
//                                return addArticle(article);
//                            } catch (ConstraintViolationException ex) {
//                                log.error(">>> Constraint violation inserting articles: {} - {}", article, ex.getMessage());
//                                status.setRollbackOnly();
//                                return null;
//                            }
//                        }).collect(Collectors.toList());
//                return created;
//            }
//        });
//    }

//    // Managing transaction directly using PlatformTransactionManager
//    public List<Article> createArticlesBatch(List<Article> articles) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        // explicitly setting the transaction name is something that can only be done programmatically
//        def.setName("createArticlesBatchTransaction");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        def.setTimeout(5);
//
//        // Do in transaction
//        TransactionStatus status = transactionManager.getTransaction(def);
//        List<Article> created = articles.stream()
//            .map(article -> {
//                try {
//                    Article resultArticle = addArticle(article);
//                    applicationEventPublisher.publishEvent(new ArticleCreationEvent(resultArticle));
//                    return resultArticle;
//                } catch (ConstraintViolationException ex) {
//                    log.error(">>> Constraint violation inserting article: {} - {}", article, ex.getMessage());
//                    transactionManager.rollback(status); // ROLLBACK
//                    throw ex;
//                }
//            }).collect(Collectors.toList());
//
//        transactionManager.commit(status); // COMMIT
//        return created;
//    }

    @TransactionalEventListener
    public void handleArticleCreatedTransactionCommit(ArticleCreationEvent creationEvent) {
        log.info(">>> Transaction COMMIT for article: {}", creationEvent.getArticle());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleArticleCreatedTransactionRollaback(ArticleCreationEvent creationEvent) {
        log.info(">>> Transaction ROLLBACK for article: {}", creationEvent.getArticle());
    }
}
