package course.spring.coredemo.model;

// Article.java

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

//import jakarta.persistence.ElementCollection;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.*;

//@Entity
public class Article {

    // Unique identifier for the article
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of the article
    private String title;

    // Description of the article
    private String content;

    // Category of the article (e.g. Technology, Sports, etc.)
    private String category;

    // Aritecle author
    private User author;

    // Tags associated with the article
//    @org.springframework.data.annotation.CollectionId
//    @ElementCollection
    private Set<String> tags;

    // Date created for the article
//    @CreatedDate
    private LocalDateTime createdAt =  LocalDateTime.now();

    // Date last updated for the article
//    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Article() {}

    public Article(String title, String content, User author, String category, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.author = author;
    }

    public Article(Long id, String title, String content, User author, String category,  Set<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.tags = tags;
    }

    public Article(Long id, String title, String content, User author, String category, Set<String> tags, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Article article)) return false;

        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(getId());
        sb.append(", title='").append(getTitle()).append('\'');
        sb.append(", content='").append(getContent()).append('\'');
        sb.append(", category='").append(getCategory()).append('\'');
        sb.append(", author='").append(getAuthor()).append('\'');
        sb.append(", tags=").append(getTags());
        sb.append(", createdAt=").append(getCreatedAt());
        sb.append(", updatedAt=").append(getUpdatedAt());
        sb.append('}');
        return sb.toString();
    }
}
