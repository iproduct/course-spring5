package org.iproduct.spring.webflux;

import org.iproduct.spring.webflux.web.ArticleHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
@EnableWebFlux
public class WebfluxStreamingDemoApplication {

	@Bean
	RouterFunction<ServerResponse> routes(ArticleHandler handler) {
		return route(GET("/api/articles"), handler::all)
				.andRoute(POST("/api/articles"), handler::create);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebfluxStreamingDemoApplication.class, args);
	}
}
