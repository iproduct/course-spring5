package org.iproduct.spring.restmvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.model.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestMvcApplication.class)
@AutoConfigureWebTestClient
@AutoConfigureJsonTesters
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
public class RestMvcBootApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private TestRestTemplate restTemplate;

    @MockBean
    private ArticleRepository articleRepository;

    @org.junit.Test
    @Test
    public void contextLoads() {
    }

    @LocalServerPort
	private int port;

    @Autowired
    ObjectMapper mapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenArticles_whenGetArticles_thenStatus200andJsonArray() throws Exception {

        given(articleRepository.findAll()).willReturn(mockArticles);

        Object exchangeMutator;
        webClient
//        		.mutateWith(mockUser("admin").password("admin").roles("ADMIN"))
                .get().uri("/api/articles").accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(Article.class).hasSize(3);
//                .returnResult(Article.class);

//        result.getResponseBody().subscribe(article -> log.info(">>> {}", article));
//                .expectBodyList(Article.class);
//                .hasSize(3).contains(mockArticles.get(0), mockArticles.get(1), mockArticles.get(2));

//                .expectBody().jsonPath("$").isArray().jsonPath("$.length()", new GreaterThan(1));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void givenArticles_whenGetArticles_thenStatus200andJsonArray_RestTemplate() throws Exception {

        given(articleRepository.findAll()).willReturn(mockArticles);
        String response = restTemplate
        		.exchange("http://localhost:" + port + "/api/articles",
                HttpMethod.GET, null, String.class).getBody();

//        List<Article> response = restTemplate.exchange("http://localhost:" + port + "/api/articles",
//                HttpMethod.GET, null, new ParameterizedTypeReference<List<Article>>() {}).getBody();
        log.info("Response: {}", response);
//        Assertions.assertEquals(mockArticles, response);
    }

    private static final List<Article> mockArticles = Arrays.asList(
            new Article("Welcome to Spring 5", "Spring 5 is great beacuse ...", "1111111111111111111111"),
            new Article("Dependency Injection", "Should I use DI or lookup ...", "1111111111111111111111"),
            new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.", "1111111111111111111111")
    );

    private static final Article newArticle =
            new Article("222222222222222222222222","New Title", "New content ...", "1111111111111111111111",
                    LocalDateTime.now(), LocalDateTime.now());
}
