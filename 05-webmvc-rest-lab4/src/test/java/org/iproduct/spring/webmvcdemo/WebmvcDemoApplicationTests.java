package org.iproduct.spring.webmvcdemo;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvcdemo.dao.ArticlesRepository;
import org.iproduct.spring.webmvcdemo.model.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Range.greaterThan;
import static java.time.LocalDateTime.now;
import static org.mockito.BDDMockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes={ WebmvcDemoApplication.class })
@AutoConfigureWebTestClient
@Slf4j
public class WebmvcDemoApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	WebTestClient webClient;

	@MockBean
	private ArticlesRepository repository;

	@LocalServerPort
	private int port;

	private MockMvc mockMvc;

	@BeforeEach
	void setup(WebApplicationContext wac) throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void givenArticles_whenGetArticles_thenStatus200andJsonArray_RestTemplate()
			throws Exception {
		given(repository.findAll()).willReturn(mockArticles);

		List<Article> articleResponse =
				restTemplate.exchange("http://localhost:" + port + "/api/articles", HttpMethod.GET, null,
						new ParameterizedTypeReference<List<Article>>() {}).getBody();

		log.info("Response {}", articleResponse);
		Assertions.assertEquals(mockArticles, articleResponse);
	}

	@Test
	public void givenArticles_whenGetArticles_thenStatus200andJsonArray_MockMvc()
			throws Exception {
		given(repository.findAll()).willReturn(mockArticles);

		mockMvc.perform(get("/api/articles").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(result -> log.info(result.getResponse().getContentAsString()))
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[0].title").value(mockArticles.get(0).getTitle()))
				.andExpect(jsonPath("$[1].title").value(mockArticles.get(1).getTitle()))
				.andExpect(jsonPath("$[2].title").value(mockArticles.get(2).getTitle()));
		then(repository).should(times(1)).findAll();
	}

	    private static final List<Article> mockArticles = Arrays.asList(
            new Article("1111111111111111111111", "Welcome to Spring 5", "Spring 5 is great beacuse ...", null, now(), now()),
            new Article("2222222222222222222222", "Dependency Injection", "Should I use DI or lookup ...", null, now(), now()),
            new Article("3333333333333333333333", "Spring Beans and Wiring", "There are several ways to configure Spring beans.", null, now(), now())
    );

	private static final Article newArticle =
			new Article("4444444444444444444444","New Title", "New content ...", "http://example.com/path/to/resource.jpg",
					LocalDateTime.now(), LocalDateTime.now());

	private static final String PATCHED_ARTICLE_TITLE = "PATCHing Web with Spring 5";
	private static final String MERGE_PATCHED_ARTICLE_TITLE = "PATCHing Web with Spring 5 Again";
	private static final String JSON_PATCH_STRING = "[{ \"op\": \"replace\", \"path\": \"/title\", \"value\": \"PATCHing Web with Spring 5\" }]";
	private static final String JSON_MERGE_PATCH_STRING = "{\"title\": \"PATCHing Web with Spring 5 Again\"}";


}
