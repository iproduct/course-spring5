package org.iproduct.spring.restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableEntityLinks;

@SpringBootApplication
@EnableEntityLinks
public class RestMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestMvcApplication.class, args);
	}
}
