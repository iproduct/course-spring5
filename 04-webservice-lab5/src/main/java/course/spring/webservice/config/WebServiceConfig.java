package course.spring.webservice.config;

import course.spring.webservice.webservice.CountryEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext ctx){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean("countries")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countrySchema) {
        DefaultWsdl11Definition def = new DefaultWsdl11Definition();
        def.setPortTypeName("CountriesPort");
        def.setLocationUri("/ws");
        def.setTargetNamespace(CountryEndpoint.NAMESPACE_URI);
        def.setSchema(countrySchema);
        Properties soapActions = new Properties();
        soapActions.setProperty("getCountry", "http://iproduct.org/course/spring-web-service/GetCountryRequest");
        def.setSoapActions(soapActions);
        return def;
    }

    @Bean("schema")
    XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }

    @Bean("jaxb2Marshaller")
    public Jaxb2Marshaller getJaxbMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        Map<String,Object> props = new HashMap<>();
        props.put("com.sun.xml.bind.namespacePrefixMapper", new PrefixMapper());
        marshaller.setMarshallerProperties(props);
        marshaller.setPackagesToScan("course.spring.webservice");
        return marshaller;
    }
}
