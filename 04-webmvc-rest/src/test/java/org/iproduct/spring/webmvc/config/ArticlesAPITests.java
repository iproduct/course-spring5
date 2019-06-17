package org.iproduct.spring.webmvc.config;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvc.dao.ArticleRepository;
import org.iproduct.spring.webmvc.model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = { SpringRootConfig.class, SpringWebConfig.class })
//@ContextHierarchy({
//        @ContextConfiguration(classes = SpringRootConfig.class),
//        @ContextConfiguration(classes = SpringWebConfig.class)
//})
//@WebAppConfiguration
//@ContextConfiguration(classes = SpringWebConfig.class)
@Slf4j
public class ArticlesAPITests {

    @MockBean
    private ArticleRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getAccount() throws Exception {
        given(repository.findAll()).willReturn(mockArticles);

        this.mockMvc.perform(get("/api/articles")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].title").value(mockArticles.get(0).getTitle()))
                .andExpect(jsonPath("$[1].title").value(mockArticles.get(1).getTitle()));
    }

    private static final List<Article> mockArticles = Arrays.asList(
            new Article("1111111111111111111111", "Welcome to Spring 5", "Spring 5 is great beacuse ...", now()),
            new Article("2222222222222222222222", "Dependency Injection", "Should I use DI or lookup ...", now()),
            new Article("3333333333333333333333", "Spring Beans and Wiring", "There are several ways to configure Spring beans.", now())
    );

    private static final Article newArticle =
            new Article("4444444444444444444444", "New Title", "New content ...", now());

    private static final String PATCHED_ARTICLE_TITLE = "PATCHing Web with Spring 5";
    private static final String MERGE_PATCHED_ARTICLE_TITLE = "PATCHing Web with Spring 5 Again";
    private static final String JSON_PATCH_STRING = "[{ \"op\": \"replace\", \"path\": \"/title\", \"value\": \"PATCHing Web with Spring 5\" }]";
    private static final String JSON_MERGE_PATCH_STRING = "{\"title\": \"PATCHing Web with Spring 5 Again\"}";
}
