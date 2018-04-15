package org.iproduct.spring.demo.beanconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {
    // equivalent to <bean id="provider" class=".."/>
//    @Bean
//    public ArticleProvider provider() {
//        return new MockArticleProvider();
//    }
//    // equivalent to <bean id="presenter" class=".."/>
//    @Bean
//    public ArticlePresenter presenter(){
//        ArticlePresenter presenter = new ConsoleArticlePresenter();
//        presenter.setArticleProvider(provider());
//        return presenter;
//    }
}
