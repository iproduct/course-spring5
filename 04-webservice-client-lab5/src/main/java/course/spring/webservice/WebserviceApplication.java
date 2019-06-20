package course.spring.webservice;

import course.spring.webservice.client.CountryClient;
import course.spring.webservice.model.Country;
import course.spring.webservice.wsdl.GetCountryResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class WebserviceApplication {

	public static void main(String[] args) {
//		SpringApplication.run(WebserviceApplication.class, args);
		ApplicationContext ctx = new AnnotationConfigApplicationContext(WebserviceApplication.class);
		CountryClient client = ctx.getBean(CountryClient.class);
		GetCountryResponse resp = client.getCountry("Bulgaria");
		Country country = new Country(resp.getCountry());
		System.out.println(country);
	}

}
