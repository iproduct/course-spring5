package course.spring.coredemo.service;

import course.spring.coredemo.util.IdGenerator;

public interface ArticleProviderFactory {
    ArticleProvider createArticleProvider(IdGenerator<Long> idGen);
}
