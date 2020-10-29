package course.spring.coredemo.consumer;

import course.spring.coredemo.formatter.ArticleFormatter;
import course.spring.coredemo.model.Article;
import course.spring.coredemo.provider.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("presenter")
public class ConsoleArticleConsumer implements  ArticleConsumer {
    @Autowired
    private Set<ArticleProvider> providers;
    private ArticleProvider provider;
    private String message;
    @Autowired
    private ArticleFormatter formatter;

    public ConsoleArticleConsumer() {
    }

    public ConsoleArticleConsumer(ArticleProvider provider) {
        this.provider = provider;
    }

    public ConsoleArticleConsumer(ArticleProvider provider, ArticleFormatter formatter) {
        this.provider = provider;
        this.formatter = formatter;
    }

    public ArticleFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(ArticleFormatter formatter) {
        this.formatter = formatter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArticleProvider getProvider() {
        return provider;
    }

    public void setProvider(ArticleProvider provider) {
        provider.addArticle(new Article("Added by property DI", "Added by property DI"));
        this.provider = provider;
    }

    public void updateProviderAndMessage(
            ArticleProvider provider,
            ArticleFormatter formatter,
            @Value("${message}") String message) {
        provider.addArticle(new Article("Added method DI", "Added method DI"));
        this.provider = provider;
        this.message = message;
        this.formatter = formatter;
    }

    @Override
    public void consume() {
        System.out.println(message);
        providers.stream().flatMap(prov -> prov.getAticles().stream()).forEach(article -> {
            System.out.println(formatter.formatArticle(article));
        });
    }
}
