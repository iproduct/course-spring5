package course.spring.restmvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String content;
    private PostUserDTO author;
    @Size(min=1, max=10)
    private Set<String> categoryTitles = new HashSet<>();
    private Set<String> keywords = new HashSet<>();
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public PostDTO() {
    }

    public Set<String> getCategoryTitles() {
        return categoryTitles;
    }

    public void setCategoryTitles(Set<String> categoryTitles) {
        this.categoryTitles = categoryTitles;
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

    public PostUserDTO getAuthor() {
        return author;
    }

    public void setAuthor(PostUserDTO author) {
        this.author = author;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDTO)) return false;
        PostDTO postDTO = (PostDTO) o;
        return Objects.equals(id, postDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PostDTO{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", author=").append(author);
        sb.append(", categoryTitles=").append(categoryTitles);
        sb.append(", keywords=").append(keywords);
        sb.append(", created=").append(created);
        sb.append(", modified=").append(modified);
        sb.append('}');
        return sb.toString();
    }
}
