package course.spring.webservice.config;

import course.spring.webservice.client.CountryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import static course.spring.webservice.client.CountryClient.ENDPOINT_URI;

@Configuration
public class CountryConfig {
   @Bean
    Jaxb2Marshaller marshaller() {
       Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
       marshaller.setContextPath("course.spring.webservice.wsdl");
       return  marshaller;
   }

   @Bean
    public CountryClient countryClient(Jaxb2Marshaller marshaller) {
       CountryClient client = new CountryClient();
       client.setDefaultUri(ENDPOINT_URI);
       client.setMarshaller(marshaller);
       client.setUnmarshaller(marshaller);
       return client;
   }
}
