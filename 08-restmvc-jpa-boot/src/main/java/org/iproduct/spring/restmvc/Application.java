package org.iproduct.spring.restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("org.iproduct.spring.restmvc")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
