package org.iproduct.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

@Document(collection="articles")
@Data
public class Article implements Identifiable<String> {
    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    @Length(min = 24, max = 24)
    private String authorId;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();
    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();

    @java.beans.ConstructorProperties({"id", "title", "content", "authorId", "created", "updated"})
    public Article(String id, String title, String content, @Length(min = 24, max = 24) String authorId, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.created = created;
        this.updated = updated;
    }

    public Article() {
    }

    public Article(String title, String content, @Length(min = 24, max = 24) String authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }
}