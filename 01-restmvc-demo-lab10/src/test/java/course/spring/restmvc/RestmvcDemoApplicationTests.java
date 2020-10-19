package course.spring.restmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import course.spring.restmvc.dao.PostsJpaRepository;
import course.spring.restmvc.model.Role;
import course.spring.restmvc.model.entity.Post;
import course.spring.restmvc.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static course.spring.restmvc.model.Role.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = RestmvcDemoApplication.class)
@AutoConfigureMockMvc
@Slf4j
class RestmvcDemoApplicationTests {
	private static User[] users =new User[] {
			new User("Default", "Admin", "admin@mycompany.com", "admin", "admin",
					new HashSet(Arrays.asList(new Role[]{ADMIN})),
					"https://e7.pngegg.com/pngimages/867/694/png-clipart-user-profile-default-computer-icons-network-video-recorder-avatar-cartoon-maker-blue-text.png"),
			new User("Default", "Author", "author@mycompany.com", "author", "author",
					new HashSet(Arrays.asList(new Role[]{AUTHOR, READER})),
					"https://www.publicdomainpictures.net/pictures/270000/nahled/man-avatar.jpg"),
	};

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostsJpaRepository postRepository;

	@Autowired
	ObjectMapper mapper;

	@Test
	void givenPosts_whenGetPosts_thenStatus200andJsonArray() throws Exception {

		given(postRepository.findAll()).willReturn(mockPosts);

		mockMvc.perform(get("/api/posts")
				.with(user("admin").password("admin").roles("ADMIN"))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andDo(result -> log.info(result.getResponse().getContentAsString()))
				.andExpect(jsonPath("$.length()").value(3))
//                .andExpect(jsonPath("$.length()").value(greaterThan(2)))
				.andExpect(jsonPath("$[0].title").value("Welcome to Spring 5"))
				.andExpect(jsonPath("$[1].title").value("Dependency Injection"))
				.andExpect(jsonPath("$[2].title").value("Spring Beans and Wireing"));
//				.andExpect(jsonPath("$._embedded.postList.length()").value(3))
////                .andExpect(jsonPath("$.length()").value(greaterThan(2)))
//				.andExpect(jsonPath("$._embedded.postList[0].title").value("Welcome to Spring 5"))
//				.andExpect(jsonPath("$._embedded.postList[1].title").value("Dependency Injection"))
//				.andExpect(jsonPath("$._embedded.postList[2].title").value("Spring Beans and Wireing"));

		then(postRepository).should(times(1)).findAll();
	}

	@Test
	void givenPosts_whenPostPost_thenStatus201andLocationHeader() throws Exception {

		given(postRepository.save(any(Post.class))).willReturn(newPost);

		mockMvc.perform(post("/api/posts")
				.with(user("admin").password("admin").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(newPost))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isCreated())
				.andExpect(header().string("location",
						containsString("http://localhost/api/posts/" + newPost.getId())));

		then(postRepository).should(times(1)).save(any(Post.class));
		verifyNoMoreInteractions(postRepository);
	}

	private static final List<Post> mockPosts = Arrays.asList(
			new Post("Welcome to Spring 5", "Spring 5 is great beacuse ...", users[1]),
			new Post("Dependency Injection", "Should I use DI or lookup ...", users[1]),
			new Post("Spring Beans and Wireing", "There are several ways to configure Spring beans.", users[1])
	);

	private static final Post newPost =
			new Post(1L,"New Title", "New content ...", users[1]);


}
