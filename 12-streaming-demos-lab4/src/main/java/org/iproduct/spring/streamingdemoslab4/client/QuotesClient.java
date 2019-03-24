package org.iproduct.spring.streamingdemoslab4.client;

import org.iproduct.spring.streamingdemoslab4.model.Quote;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Properties;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

public class QuotesClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Properties props = new Properties();
        ClassPathResource resource = new ClassPathResource("application.properties");
        props.load(resource.getInputStream());

        String baseUrl = "http://localhost:" + props.getProperty("server.port");

        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        System.out.println("QuotesClient constructed @ " + baseUrl);

        client.get().uri("/api/quotes")
            .accept(APPLICATION_STREAM_JSON)
            .exchange()
//            .log()
            .flatMapMany(resp -> resp.bodyToFlux(Quote.class))
            .subscribe(
                System.out::println,
                err -> System.err.println(err.getMessage()),
                () -> System.out.println("Stream completed.")
            );

        Thread.sleep(18000);
    }
}
