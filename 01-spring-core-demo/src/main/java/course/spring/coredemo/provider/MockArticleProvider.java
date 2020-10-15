package course.spring.coredemo.provider;

import course.spring.coredemo.model.Article;

import java.util.Arrays;
import java.util.List;

public class MockArticleProvider implements ArticleProvider {
    @Override
    public List<Article> getAticles() {
        return Arrays.asList(new Article[] {
                new Article("New Spring 5", "WebFlux is here ..."),
                new Article("DI Basics", "There are many ways to DI in Spring ..."),
                new Article("Reactive Spring", "WebFlux is based on project Ractor  ...")
        });
    }
}
