package org.iproduct.spring.webmvc.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


public class Article {
    @NotNull
    private long id = System.nanoTime();

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

    public Article() {
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdDate = new Date();
    }

    public Article(String title, String content, LocalDateTime createdDate) {
        this.title = title;
        this.content = content;
        this.createdDate = new Date();
    }

    public Article(@NotNull long id,
                   @NotNull @Length(min = 2, max = 40) String title,
                   @NotNull @Length(min = 2, max = 1000) String content,
                   @NotNull @PastOrPresent Date createdDate,
                   String pictureUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.pictureUrl = pictureUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return  Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", pictureUrl='").append(pictureUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}