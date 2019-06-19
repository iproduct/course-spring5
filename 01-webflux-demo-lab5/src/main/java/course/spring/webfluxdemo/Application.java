package course.spring.webfluxdemo;

import course.spring.webfluxdemo.web.ArticlesReactiveHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class Application {

	@Bean
	public RouterFunction<ServerResponse> routes(ArticlesReactiveHandler handler) {
		RouterFunction<ServerResponse> articleRoutes =
				route(GET("/"), handler::getAllArticles)
				.andRoute(POST("/"), handler::addArticle)
				.andRoute(GET("/{id}"), handler::getArticleById)
				.andRoute(PUT("/{id}"), handler::updateArticle)
				.andRoute(DELETE("/{id}"), handler::deleteArticleById);
		return nest(RequestPredicates.path("/api/reactive/articles"), articleRoutes);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
