package org.iproduct.spring.restmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.model.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestMvcApplication.class)
@AutoConfigureWebTestClient
@AutoConfigureJsonTesters
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
public class RestMvcBootApplicationTests {

    public static final String AUTH_TOKEN = "JSESSIONID";

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private TestRestTemplate restTemplate;

    @MockBean
    private ArticleRepository articleRepository;

    private String authToken;

    @BeforeEach
    void setup(WebApplicationContext wac) throws Exception {
        authToken = webClient.post().uri("/login").contentType(APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("username", "admin").with("password", "admin"))
                .exchange().returnResult(String.class).getResponseCookies().get(AUTH_TOKEN).get(0).getValue();
        log.info(">>> AUTH Token: {}", authToken);
    }

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
        WebTestClient.ResponseSpec response = webClient
//        		.mutateWith(mockUser("admin").password("admin").roles("ADMIN"))
                .mutate().defaultCookie(AUTH_TOKEN, authToken).build()
                .get().uri("/api/articles").accept(MediaType.APPLICATION_JSON)
                .exchange();

//        log.info(">>>>> Result: {}", response.returnResult(String.class));

        response.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("._embedded.articles").isArray()
                .jsonPath("._embedded.articles.length()").isEqualTo(mockArticles.size())
                .jsonPath("$._embedded.articles[0].title").isEqualTo(mockArticles.get(0).getTitle())
                .jsonPath("$._embedded.articles[1].title").isEqualTo(mockArticles.get(1).getTitle())
                .jsonPath("$._embedded.articles[2].title").isEqualTo(mockArticles.get(2).getTitle());

//        result.getResponseBody().subscribe(article -> log.info(">>> {}", article));
//                .expectBodyList(Article.class);
//                .hasSize(3).contains(mockArticles.get(0), mockArticles.get(1), mockArticles.get(2));

//                .expectBody().jsonPath("$").isArray().jsonPath("$.length()", new GreaterThan(1));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void givenArticles_whenGetArticles_thenStatus200andJsonArray_RestTemplate() throws Exception {

        given(articleRepository.findAll()).willReturn(mockArticles);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Cookie", AUTH_TOKEN + "=" + authToken);

        String response = restTemplate
        		.exchange("http://localhost:" + port + "/api/articles",
                HttpMethod.GET, new HttpEntity<Void>(null, requestHeaders), String.class).getBody();

//        List<Article> response = restTemplate.exchange("http://localhost:" + port + "/api/articles",
//                HttpMethod.GET, null, new ParameterizedTypeReference<List<Article>>() {}).getBody();
        log.info("Response: {}", response);

        Configuration.setDefaults(new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        int articlesSize = JsonPath.parse(response).read("$._embedded.articles.length()", Integer.class );
        String article0title = JsonPath.parse(response).read("$._embedded.articles[0].title", String.class );
        String article1title = JsonPath.parse(response).read("$._embedded.articles[1].title", String.class );
        String article2title = JsonPath.parse(response).read("$._embedded.articles[2].title", String.class );
        log.info("Article titles [{}]: {}, {}, {}", articlesSize, article0title, article1title, article2title);
        Assertions.assertEquals(mockArticles.size(), articlesSize);
        Assertions.assertEquals(mockArticles.get(0).getTitle(), article0title);
        Assertions.assertEquals(mockArticles.get(1).getTitle(), article1title);
        Assertions.assertEquals(mockArticles.get(2).getTitle(), article2title);
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
