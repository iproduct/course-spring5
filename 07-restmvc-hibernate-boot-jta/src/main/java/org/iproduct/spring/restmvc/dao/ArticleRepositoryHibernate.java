package org.iproduct.spring.restmvc.dao;

import org.hibernate.SessionFactory;
import org.iproduct.spring.restmvc.exception.EntityNotFoundException;
import org.iproduct.spring.restmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional
public class ArticleRepositoryHibernate implements ArticleRepository {

    private TransactionTemplate transactionTemplate;
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Collection<Article> findAll() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select article from Article article", Article.class)
                .list();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findById(long id) {
        return this.sessionFactory.getCurrentSession()
                .byId(Article.class).loadOptional(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select count(article) from Article article", Long.class)
                .uniqueResult();
    }

    @Override
    @Transactional
    public Article insert(Article article) {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                sessionFactory.getCurrentSession()
                        .saveOrUpdate(article);
            }
        });

        return article;
    }

    @Override
    @Transactional
    public Article save(Article article) {
        Optional<Article> toBeDeleted = findById(article.getId());
        if (!toBeDeleted.isPresent()) {
            throw new EntityNotFoundException("Article with ID=" + article.getId() + " does not exist.");
        }
       return (Article) this.sessionFactory.getCurrentSession()
                .merge(article);
    }

    @Override
    @Transactional
    public Optional<Article> deleteById(long articleId) {
        Optional<Article> toBeDeleted = findById(articleId);
        if (!toBeDeleted.isPresent()) {
            throw new EntityNotFoundException("Article with ID=" + articleId + " does not exist.");
        }
        this.sessionFactory.getCurrentSession()
                .delete(toBeDeleted);
        return toBeDeleted;
    }
}
