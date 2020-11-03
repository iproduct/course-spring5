package course.spring.restmvc.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @SequenceGenerator(name = "postSeqGen", sequenceName = "postSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeqGen")
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name="author_id", nullable = false, updatable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name="fkPostsUsersId"))
    private User author;

    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords = new HashSet<>();

    LocalDateTime created = LocalDateTime.now();
    LocalDateTime modified = LocalDateTime.now();

    public Post(@NonNull String title, @NonNull String content, @NonNull User author, @NonNull Set<String> keywords) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.keywords = keywords;
    }
}
