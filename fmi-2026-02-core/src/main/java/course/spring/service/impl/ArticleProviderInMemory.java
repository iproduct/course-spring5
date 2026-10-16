package course.spring.service.impl;

import course.spring.model.Article;
import course.spring.service.ArticleProvider;
import jakarta.annotation.PostConstruct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static course.spring.model.Category.*;

public class ArticleProviderInMemory implements ArticleProvider {
    public static final List<Article> SAMPLE_ARTICLES =List.of(
            new Article("New in Spring 7", "Spring AI is a new Spring feature API ...", "Trayan Iliev",
                    Set.of(PROGRAMMING, AI), Set.of("spring", "ai", "new")),
            new Article("DI in Spring", "Dependency injection is major Spring feature ...", "Trayan Iliev",
                    Set.of(PROGRAMMING), Set.of("spring", "platform", "core")),
            new Article("Running Spring AI Locally", "This post demonstrates how to run LLMs with Ollama and Spring AI ...", "Trayan Iliev",
                    Set.of(PROGRAMMING, AI), Set.of("spring", "ai", "ollama", "llm"))
    );
    private Map<Long, Article> articles = new HashMap<>();
    @PostConstruct
    public void init(){

    }
    @Override
    public List<Article> getArticles() {
        return List.of();
    }
}
