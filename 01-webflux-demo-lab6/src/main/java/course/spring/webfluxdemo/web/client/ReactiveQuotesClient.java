package course.spring.webfluxdemo.web.client;

import course.spring.webfluxdemo.model.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

@Slf4j
public class ReactiveQuotesClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Properties prop = new Properties();
        InputStream input = ReactiveQuotesClient.class.getClassLoader()
                .getResourceAsStream("application.properties");
        prop.load(input);
        String baseUrl = "http://localhost:" + prop.getProperty("server.port");
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        log.info("Quotes client constructed @" + baseUrl);
        client.get().uri("/api/quotes")
                .accept(APPLICATION_STREAM_JSON)
                .exchange()
                .flatMapMany(resp -> resp.bodyToFlux(Quote.class))
                .subscribe(System.out::println,
                        System.err::println,
                        ()-> System.out.println("Quotes stream completed"));
        Thread.sleep(20000);
    }
}
