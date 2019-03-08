package patchdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import patchdemo.dao.ArticlesRepository;
import patchdemo.init.DataInitializer;
import patchdemo.model.Article;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static patchdemo.init.DataInitializer.SAMPLE_ARTICLES;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PatchDemoApplication.class)
@AutoConfigureWebTestClient
@Slf4j
public class PatchDemoApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private TestRestTemplate restTemplate;

//    @MockBean
//    private ArticlesRepository articleRepository;

    @Autowired
    private ArticlesRepository articleRepository;

    @LocalServerPort
	private int port;

    @Autowired
    ObjectMapper mapper;

    private List<Article> sampleArticles;

    @BeforeEach
    public void init() {
        sampleArticles = articleRepository.findAll();
        log.info("Testing with articles: {}", sampleArticles);
    }

    @Test
    public void givenArticles_whenGetArticles_thenStatus200andJsonArray() throws Exception {
//        given(articleRepository.findAll()).willReturn(mockArticles);

        List<Article> articlesResult = webClient
                .get().uri("/api/articles").accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(Article.class).hasSize(3)
                .contains(sampleArticles.toArray(new Article[]{}))
                .returnResult().getResponseBody();

        log.info(">>> {}", articlesResult);
    }

    @Test
    public void givenArticles_whenGetArticles_thenStatus200andJsonArray_RestTemplate() throws Exception {
//        given(articleRepository.findAll()).willReturn(mockArticles);

        List<Article> response = restTemplate.exchange("http://localhost:" + port + "/api/articles",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Article>>() {}).getBody();
        log.info("Response: {}", response);
        Assertions.assertEquals(sampleArticles, response);
    }

    @Test
    public void givenArticles_whenPatchArticle_thenStatus200andArticlePatched() throws Exception {
//        given(articleRepository.findAll()).willReturn(mockArticles);

        Article patchedResult = webClient
                .patch()
                .uri( UriComponentsBuilder
                        .fromUriString("http://localhost:{port}/api/articles/{articleId}")
                        .build(port, sampleArticles.get(0).getId()) )
                .header(HttpHeaders.CONTENT_TYPE, "application/json-patch+json")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("UTF-8"))
                .syncBody(JSON_PATCH_STRING)

                .exchange().expectStatus().isOk()
                .expectBody(Article.class)
                .returnResult().getResponseBody();

        log.info(">>> Patched result: {}", patchedResult);

        Assertions.assertEquals(PATCHED_ARTICLE_TITLE, patchedResult.getTitle());
    }

    @Test
    public void givenArticles_whenMergePatchArticle_thenStatus200andArticlePatched() throws Exception {
//        given(articleRepository.findAll()).willReturn(mockArticles);

        Article patchedResult = webClient
                .patch()
                .uri( UriComponentsBuilder
                        .fromUriString("http://localhost:{port}/api/articles/{articleId}")
                        .build(port, sampleArticles.get(0).getId()) )
                .header(HttpHeaders.CONTENT_TYPE, "application/merge-patch+json")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("UTF-8"))
                .syncBody(JSON_MERGE_PATCH_STRING)

                .exchange().expectStatus().isOk()
                .expectBody(Article.class)
                .returnResult().getResponseBody();

        log.info(">>> Patched result: {}", patchedResult);

        Assertions.assertEquals(MERGE_PATCHED_ARTICLE_TITLE, patchedResult.getTitle());
    }

//    private static final List<Article> mockArticles = Arrays.asList(
//            new Article("1111111111111111111111", "Welcome to Spring 5", "Spring 5 is great beacuse ..."),
//            new Article("2222222222222222222222", "Dependency Injection", "Should I use DI or lookup ..."),
//            new Article("3333333333333333333333", "Spring Beans and Wiring", "There are several ways to configure Spring beans.")
//    );

    private static final Article newArticle =
            new Article("4444444444444444444444","New Title", "New content ...", "http://example.com/path/to/resource.jpg",
                    LocalDateTime.now(), LocalDateTime.now());

    private static final String PATCHED_ARTICLE_TITLE = "PATCHing Web with Spring 5";
    private static final String MERGE_PATCHED_ARTICLE_TITLE = "PATCHing Web with Spring 5 Again";
    private static final String JSON_PATCH_STRING = "[{ \"op\": \"replace\", \"path\": \"/title\", \"value\": \"PATCHing Web with Spring 5\" }]";
    private static final String JSON_MERGE_PATCH_STRING = "{\"title\": \"PATCHing Web with Spring 5 Again\"}";
}
