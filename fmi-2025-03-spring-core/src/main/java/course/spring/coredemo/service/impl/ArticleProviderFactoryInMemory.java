package course.spring.coredemo.service.impl;

import course.spring.coredemo.service.ArticleProvider;
import course.spring.coredemo.service.ArticleProviderFactory;
import course.spring.coredemo.util.IdGenerator;

public class ArticleProviderFactoryInMemory implements ArticleProviderFactory {
    @Override
    public ArticleProvider createArticleProvider(IdGenerator<Long> idGen) {
        return new ArticleProviderInMemory(idGen);
    }
}
