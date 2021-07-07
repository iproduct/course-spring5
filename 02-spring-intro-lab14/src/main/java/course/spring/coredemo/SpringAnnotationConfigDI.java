package course.spring.coredemo;

import course.spring.coredemo.dao.ArticleProvider;
import course.spring.coredemo.dao.impl.MockArticleProvider;

public class SpringAnnotationConfigDI {
    public static void main(String[] args) {
        ArticleProvider provider = new MockArticleProvider();
        System.out.println(provider.getArticles());
    }
}
