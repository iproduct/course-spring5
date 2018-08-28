package org.iproduct.spring.restmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.restmvc.config.SpringRootConfig;
import org.iproduct.spring.restmvc.config.SpringSecurityConfig;
import org.iproduct.spring.restmvc.config.SpringWebConfig;
import org.iproduct.spring.restmvc.dao.ArticleRepository;
import org.iproduct.spring.restmvc.model.Article;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = RestMvcBootLab2Application.class)
@SpringJUnitWebConfig({SpringRootConfig.class, SpringWebConfig.class, SpringSecurityConfig.class})
@AutoConfigureMockMvc
//@AutoConfigureWebTestClient
@AutoConfigureJsonTesters
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
public class RestMvcBootLab2ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

//	@Autowired
//	private WebTestClient webClient;

//	@Autowired
//	private TestRestTemplate restTemplate;

	@MockBean
	private ArticleRepository articleRepository;

	@org.junit.Test
	@Test
	public void contextLoads() {
	}

//	@LocalServerPort
//	private int port;

	@Autowired
	ObjectMapper mapper;

//	@Test
//	public void givenArticles_whenGetArticles_thenStatus200andJsonArray() throws Exception {
//
//
////		String response= restTemplate.getForObject("http://localhost:" + port + "/api/articles",	String.class);
////		log.info("Response: {}", response);
//
//
//		webClient.get().uri("/api/articles").accept(MediaType.APPLICATION_JSON)
//				.exchange().expectStatus().isOk()
//				.expectBody().jsonPath("$").isArray()
//				.jsonPath("$.length()", new GreaterThan(1));
//
//	}

	@Test
	public void givenArticles_whenGetArticles_thenStatus200andJsonArray() throws Exception {
		given(articleRepository.findAll()).willReturn(mockArticles);

		mockMvc.perform(get("/api/articles").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[0].title").value("Welcome to Spring 5"))
				.andExpect(jsonPath("$[1].title").value("Dependency Injection"))
				.andExpect(jsonPath("$[2].title").value("Spring Beans and Wireing"));

		then(articleRepository).should(times(1)).findAll();
	}

	private static final List<Article> mockArticles = Arrays.asList(
			new Article("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
			new Article("Dependency Injection", "Should I use DI or lookup ..."),
			new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans.")
	);

	private static final Article newArticle =
			new Article("222222222222222222222222","New Title", "New content ...", LocalDateTime.now());

}
