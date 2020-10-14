package course.spring.restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource
public class RestmvcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestmvcDemoApplication.class, args);
	}

}
