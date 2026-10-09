package course.spring.intro.init;

import course.spring.intro.entity.Article;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Set;

import static course.spring.intro.entity.Category.*;

public class DbInitializer implements CommandLineRunner {
    public static final List<Article> articles = List.of(
            new Article("New in Spring 7", "Spring AI is a new Spring feature API ...", "Trayan Iliev",
                    Set.of(PROGRAMMING, AI), Set.of("spring", "ai", "new")),
            new Article("DI in Spring", "Dependency injection is major Spring feature ...", "Trayan Iliev",
                    Set.of(PROGRAMMING), Set.of("spring", "platform", "core")),
            new Article("Running Spring AI Locally", "This post demonstrates how to run LLMs with Ollama and Spring AI ...", "Trayan Iliev",
                    Set.of(PROGRAMMING, AI), Set.of("spring", "ai", "ollama", "llm"))
    );

    @Override
    public void run(String... args) throws Exception {

    }
}
