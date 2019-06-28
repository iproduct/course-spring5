package course.spring.webservice;

import course.spring.webservice.model.Country;
import course.spring.webservice.webclient.CountryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class WebserviceDemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				"course.spring.webservice");
		CountryClient client = ctx.getBean(CountryClient.class);
		System.out.println(">>>> " +
				new Country(client.getCountry("Bulgaria").getCountry()));
//		SpringApplication.run(WebserviceDemoApplication.class, args);
	}

}
