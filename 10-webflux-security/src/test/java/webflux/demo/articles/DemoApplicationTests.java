package webflux.demo.articles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.net.URI;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    ApplicationContext context;

    WebTestClient client;

    @BeforeEach
    public void setup() {
        client = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .baseUrl("http://localhost:8080/")
                .build();
    }

    @Test
    public void getAllPostsShouldBeOkWithoutAuthetication() {
        client
                .get()
                .uri("/articles/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getANoneExistedPostShouldReturn404() {
        client
                .get()
                .uri("/articles/ABC")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void createAPostNotAllowedWhenIsNotAuthorized() {
        client
                .post()
                .uri("/articles")
                .body(BodyInserters.fromValue(Article.builder().title("Article test").content("content of post test").build()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void deletePostsNotAllowedWhenIsNotAdmin() {
        client
                .mutate().filter(basicAuthentication("admin", "user")).build()
                .delete()
                .uri("/articles/1")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void deletePostsAllowedWhenAdmin() {
        client
                .mutate().filter(basicAuthentication("admin", "admin")).build()
                .delete()
                .uri("/articles/1")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void postCrudOperations() {
        int randomInt = new Random().nextInt();
        String title = "Article test " + randomInt;
        FluxExchangeResult<Void> postResult = client
                .mutate().filter(basicAuthentication("admin", "admin")).build()
                .post()
                .uri("/articles")
                .body(BodyInserters.fromValue(Article.builder().title(title).content("content of " + title).build()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .returnResult(Void.class);

        URI location = postResult.getResponseHeaders().getLocation();
        assertNotNull(location);

        EntityExchangeResult<byte[]> getResult = client
                .mutate().filter(basicAuthentication("admin", "admin")).build()
                .get()
                .uri(location)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.title").isEqualTo(title)
                .returnResult();

        String getPost = new String(getResult.getResponseBody());
        assertTrue(getPost.contains(title));
    }

}
