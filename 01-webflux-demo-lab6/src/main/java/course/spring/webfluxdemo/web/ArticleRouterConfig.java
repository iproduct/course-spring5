package course.spring.webfluxdemo.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ArticleRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ReactiveArticleHandler handler) {
        return route(GET("/api/reactive/articles"), handler::getAllArticles);
    }
}
