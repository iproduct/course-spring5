package course.spring.service.impl;

import course.spring.dao.ArticlesRepository;
import course.spring.model.Article;
import course.spring.service.ArticleProvider;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.model.Category.*;

public class ArticleProviderInMemory implements ArticleProvider {
    public static final List<Article> SAMPLE_ARTICLES =List.of(
            new Article(1L, "New in Spring 7", "Spring AI is a new Spring feature API ...", "Trayan Iliev",
                    Set.of(PROGRAMMING, AI), Set.of("spring", "ai", "new")),
            new Article(2L, "DI in Spring", "Dependency injection is major Spring feature ...", "Trayan Iliev",
                    Set.of(PROGRAMMING), Set.of("spring", "platform", "core")),
            new Article(3L, "Running Spring AI Locally", "This post demonstrates how to run LLMs with Ollama and Spring AI ...", "Trayan Iliev",
                    Set.of(PROGRAMMING, AI), Set.of("spring", "ai", "ollama", "llm"))
    );

    private ArticlesRepository articlesRepo;
    public ArticleProviderInMemory(ArticlesRepository articlesRepo) {
        this.articlesRepo = articlesRepo;
    }

    public static ArticleProviderInMemory createInstance(ArticlesRepository articlesRepo) {
        return new ArticleProviderInMemory(articlesRepo);
    }

    public void init(){
        SAMPLE_ARTICLES.forEach(this::addArticle);
    }


    @Override
    public List<Article> getArticles() {
        return articlesRepo.findAll();
    }

    @Override
    public Article addArticle(Article article) {
        return articlesRepo.create(article);
    }
}
