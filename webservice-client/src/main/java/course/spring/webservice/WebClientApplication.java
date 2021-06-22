package course.spring.webservice;

import course.spring.webservice.model.Country;
import course.spring.webservice.webservice.CountryClient;
import course.spring.webservice.wsdl.GetCountryResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class WebClientApplication {

	public static void main(String[] args) {
//		SpringApplication.run(WebClientApplication.class, args);
		ApplicationContext ctx = new AnnotationConfigApplicationContext(WebClientApplication.class);
		CountryClient countryClient = ctx.getBean(CountryClient.class);
		String country = "Bulgaria";

		if (args.length > 0) {
			country = args[0];
		}
		GetCountryResponse response = countryClient.getCountry(country);
		Country respCountry = new Country(response.getCountry());
		System.err.println(respCountry);
	}

//	@Bean
//	CommandLineRunner lookup(CountryClient quoteClient) {
//		return args -> {
//			String country = "Bulgaria";
//
//			if (args.length > 0) {
//				country = args[0];
//			}
//			GetCountryResponse response = quoteClient.getCountry(country);
//			Country respCountry = new Country(response.getCountry());
//			System.err.println(respCountry);
//		};
//	}

}
