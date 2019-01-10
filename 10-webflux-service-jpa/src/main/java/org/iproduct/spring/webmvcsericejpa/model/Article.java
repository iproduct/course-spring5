package org.iproduct.spring.webmvcsericejpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name="articles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Length(min = 2, max = 40)
    private String title;

    @NotNull
    @Length(min = 2, max = 1000)
    private String content;

    @NotNull
    @PastOrPresent
    private Date createdDate;

    private String pictureUrl;

    public Article(
            @NotNull @Length(min = 2, max = 40) String title,
            @NotNull @Length(min = 2, max = 1000) String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        createdDate = new Date();
    }
}