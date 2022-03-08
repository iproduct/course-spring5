package course.ws.blogs;

import course.ws.blogs.dao.ArticleRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.Role;
import course.ws.blogs.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
class BlogsApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleRepository articleRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@WithMockUser(roles={"ADMIN"})
	void givenArticles_whenGetArticles_thenStatusOKJsonArray() throws Exception {
		// setup
		given(articleRepository.findAll()).willReturn(SAMPLE_ARTICLES);

		// action
		mockMvc.perform(get("/api/articles").accept(APPLICATION_JSON))
				// assertions
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(SAMPLE_ARTICLES.size()))
				.andExpect(jsonPath("$[0].title").value(SAMPLE_ARTICLES.get(0).getTitle()))
				.andDo(result -> log.info(result.getResponse().getContentAsString()));

		then(articleRepository).should(times(1)).findAll();
		then(articleRepository).shouldHaveNoMoreInteractions();
	}

	private static final List<User> SAMPLE_USERS = List.of(
			new User(1L, "Default", "Admin", "admin", "admin123", Role.ADMIN),
			new User(2L, "Default", "Author", "author", "admin123", Role.AUTHOR),
			new User(3L, "Default", "Reader", "reader", "reader123", Role.READER)
	);
	private static final List<Article> SAMPLE_ARTICLES = List.of(
			new Article(1L, "Spring Data JPA Intro",
					"Spring Data JPA is easy ...",
					SAMPLE_USERS.get(1), Set.of("spring", "mvc", "boot", "intro")),
			new Article(2L,"Spring Data JPA and Hibernate",
					"Hibernate provides powerful ORM implementation ...",
					SAMPLE_USERS.get(1), Set.of("hibernate", "performance")),
			new Article(3L, "Spring Data is Going Reactive",
					"Spring Data provides reactive db integrations for a number of databases ...",
					SAMPLE_USERS.get(1), Set.of("spring", "boot", "intro"))

	);


}
