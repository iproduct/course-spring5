package org.iproduct.spring.webmvc.dao;

import org.hibernate.SessionFactory;
import org.iproduct.spring.webmvc.exceptions.EntityDoesNotExistException;
import org.iproduct.spring.webmvc.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
//@Transactional
public class ArticleDaoHibernate implements ArticleDao {

    private TransactionTemplate transactionTemplate;
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    @Autowired
//    private EntityManager em ;

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
    public Article find(long id) {
        return this.sessionFactory.getCurrentSession()
                .byId(Article.class).load(id);
    }

    @Override
    public Article create(Article article) {
        this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                sessionFactory.getCurrentSession()
                        .persist(article);
            }
        });

        return article;
    }

    @Override
    @Transactional
    public Article update(Article article) {
        Article toBeDeleted = find(article.getId());
        if (toBeDeleted == null) {
            throw new EntityDoesNotExistException("Article with ID=" + article.getId() + " does not exist.");
        }

        return (Article) this.sessionFactory.getCurrentSession()
                .merge(article);
    }

    @Override
    @Transactional
    public Article remove(long articleId) {
        Article toBeDeleted = find(articleId);
        if (toBeDeleted == null) {
            throw new EntityDoesNotExistException("Article with ID=" + articleId + " does not exist.");
        }
        this.sessionFactory.getCurrentSession()
                .delete(toBeDeleted);
        return toBeDeleted;
    }
}
