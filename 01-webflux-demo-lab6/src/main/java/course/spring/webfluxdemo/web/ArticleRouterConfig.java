package course.spring.webfluxdemo.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ArticleRouterConfig {

    @Bean("articleRoutes")
    public RouterFunction<ServerResponse> articleRoutes(ReactiveArticleHandler handler) {
        return route(GET("/"), handler::getAllArticles)
            .andRoute(GET("/{id}"), handler::getArticleById)
            .andRoute(POST("/"), handler::addArticle)
            .andRoute(PUT("/{id}"), handler::updateArticle)
            .andRoute(DELETE("/{id}"), handler::deleteArticleById);

    }
}
