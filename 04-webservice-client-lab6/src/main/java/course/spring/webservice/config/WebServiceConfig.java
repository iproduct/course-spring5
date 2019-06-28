package course.spring.webservice.config;

import course.spring.webservice.webclient.CountryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

import static course.spring.webservice.webclient.CountryClient.WEB_SERVICE_URI;

@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("course.spring.webservice.wsdl");
        return marshaller;
    }

    @Bean
    public CountryClient countryClient(Jaxb2Marshaller marshaller) {
        CountryClient client = new CountryClient();
        client.setDefaultUri(WEB_SERVICE_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
