package course.spring.webfluxdemo;

import course.spring.webfluxdemo.web.ReactiveArticleHandler;
import course.spring.webfluxdemo.web.ReactiveQuotesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class WebfluxDemoApplication {

	@Autowired
	private ReactiveArticleHandler articleHandler;
	@Autowired
	ReactiveQuotesHandler quotesHandler;

	public RouterFunction<ServerResponse> articleRoutes() {
		return route(GET("/"), articleHandler::getAllArticles)
				.andRoute(GET("/{id}"), articleHandler::getArticleById)
				.andRoute(POST("/"), articleHandler::addArticle)
				.andRoute(PUT("/{id}"), articleHandler::updateArticle)
				.andRoute(DELETE("/{id}"), articleHandler::deleteArticleById);
	}

	public RouterFunction<ServerResponse> quotesRoutes() {
		return route(GET("/").and(accept(APPLICATION_STREAM_JSON)),
					quotesHandler::streamQuotes)
				.andRoute(GET("/").and(accept(TEXT_EVENT_STREAM)),
					quotesHandler::streamQuotesSSE);
	}

	@Bean
	public RouterFunction<ServerResponse> routes() {
		return nest(path("/api/reactive/articles"), articleRoutes())
				.andNest(path("/api/quotes"), quotesRoutes());
	}

	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoApplication.class, args);
	}

}
