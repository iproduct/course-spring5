package course.spring.blogs.events;

import course.spring.blogs.entity.Article;
import lombok.Value;

public record ArticleCreatedEvent(Article article) {
}
