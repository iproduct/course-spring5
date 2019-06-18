package course.spring.webfluxdemo;

import course.spring.webfluxdemo.web.ArticlesReactiveHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class Application {

	@Bean
	public RouterFunction<ServerResponse> routes(ArticlesReactiveHandler handler) {
		return route(GET("/api/reactive/articles"), handler::getAllArticles);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
