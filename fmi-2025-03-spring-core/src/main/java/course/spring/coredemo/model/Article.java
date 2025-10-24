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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    // Tags associated with the article
//    @org.springframework.data.annotation.CollectionId
//    @ElementCollection
    private List<String> tags;

    // Date created for the article
//    @CreatedDate
    private LocalDateTime createdAt;

    // Date last updated for the article
//    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Article() {}

    public Article(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = new ArrayList<>();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
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
}
