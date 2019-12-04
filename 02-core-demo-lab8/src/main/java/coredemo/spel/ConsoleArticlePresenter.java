package coredemo.spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("presenter")
public class ConsoleArticlePresenter implements ArticlePresenter {
    @Value("#{provider}")
//    @Autowired
    private ArticleProvider provider;

    public ConsoleArticlePresenter() {
    }

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        if (provider == null) {
            throw new RuntimeException(
                    "Error: Provider not set. Please set provider for articles before presenting them.");
        }
        provider.getArticles().forEach(System.out::println);
    }

    @Override
    public void setArticleProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public ArticleProvider getArticleProvider() {
        return provider;
    }
}
