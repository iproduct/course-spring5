package course.ws.blogs;

import course.ws.blogs.dao.ArticleRepository;
import course.ws.blogs.entity.Article;
import course.ws.blogs.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;

import static course.ws.blogs.entity.Role.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BlogsApiApplicationTests {

	@MockBean
	private ArticleRepository mockArticleRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void givenArticles_whenGetArticles_thenStatus200JsonArray() {
		given(mockArticleRepository.findAll()).willReturn(SAMPLE_ARTICLES);
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
