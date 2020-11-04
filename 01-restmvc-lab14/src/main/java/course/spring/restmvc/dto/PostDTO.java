package course.spring.restmvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import course.spring.restmvc.entity.Category;
import course.spring.restmvc.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
}
