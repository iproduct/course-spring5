package org.iproduct.spring.restmvc.web.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Relation(value="article", collectionRelation="articles")
@JsonPropertyOrder({ "id", "title", "content", "authorId", "created", "updated"})
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
public class ArticleResource extends ResourceWithEmbeddeds {

    @JsonProperty(value="id")
    private String articleId;

    private String title;
    private String content;
    private String authorId;
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();

    @JsonCreator
    public ArticleResource(String id, String title, String content, String authorId, LocalDateTime created, LocalDateTime updated) {
        this.articleId = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.created = created;
        this.updated = updated;
    }
}
