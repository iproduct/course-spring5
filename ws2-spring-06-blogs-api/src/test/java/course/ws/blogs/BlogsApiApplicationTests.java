package course.ws.blogs;

import course.ws.blogs.dao.ArticleRepository;
import course.ws.blogs.dao.UserRepository;
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

import static course.ws.blogs.entity.Role.*;
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
	private ArticleRepository mockArticleRepository;

	@MockBean
	private UserRepository mockUserRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void givenArticles_whenGetArticles_thenStatus200JsonArray() throws Exception {
		//setup
		given(mockArticleRepository.findAll()).willReturn(SAMPLE_ARTICLES);
		// action
		mockMvc.perform(get("/api/articles").accept(APPLICATION_JSON))
				// assert
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(SAMPLE_ARTICLES.size()))
				.andExpect(jsonPath("$[0].title").value(SAMPLE_ARTICLES.get(0).getTitle()))
				.andDo(result -> log.info(result.getResponse().getContentAsString()));

		// check mock methods called
		then(mockArticleRepository).should(times(1)).findAll();
	}

	@Test
	@WithMockUser(roles={"ADMIN"})
	void givenUsers_whenGetUsers_thenStatus200JsonArray() throws Exception {
		//setup
		given(mockUserRepository.findAll()).willReturn(DEFAULT_USERS);
		// action
		mockMvc.perform(get("/api/users").accept(APPLICATION_JSON))
				// assert
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(DEFAULT_USERS.size()))
				.andExpect(jsonPath("$[0].email").value(DEFAULT_USERS.get(0).getEmail()))
				.andDo(result -> log.info(result.getResponse().getContentAsString()));

		// check mock methods called
		then(mockUserRepository).should(times(1)).findAll();
	}

	public static final List<User> DEFAULT_USERS = List.of(
			new User(1L, "Default", "Admin", "admin@mydomain.com", "admin", ADMIN),
			new User(2L, "Default", "Author", "author@mydomain.com", "author", AUTHOR),
			new User(3L, "Default", "Reader", "reader@mydomain.com", "reader", READER)
	);
	public static final List<Article> SAMPLE_ARTICLES = List.of(
			new Article(1L,"Intro to Spring", "Spring MVC is easy ...",
					DEFAULT_USERS.get(1), Set.of("spring", "mvc", "boot", "intro")),
			new Article(2L,"Hibernate Performance", "Hibernate provides powerful ORM implementation ...",
					DEFAULT_USERS.get(1), Set.of("hibernate", "performance")),
			new Article( 3L,"Spring Boot is Easy", "Spring Boot makes bootstrapping new Spring projects easy ...",
					DEFAULT_USERS.get(1), Set.of("spring", "boot", "intro"))
	);

}
