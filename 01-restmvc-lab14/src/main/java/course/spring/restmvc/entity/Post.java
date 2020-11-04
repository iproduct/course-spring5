package course.spring.restmvc.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @SequenceGenerator(name = "postSeqGen", sequenceName = "postSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeqGen")
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    @Lob
    @Basic(optional = false, fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name="author_id", nullable = false, updatable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name="fkPostsUsersId"))
    private User author;

    @NonNull
    @ManyToMany(mappedBy = "posts", fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();;

    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords = new HashSet<>();

    LocalDateTime created = LocalDateTime.now();
    LocalDateTime modified = LocalDateTime.now();

    public Post(@NonNull String title, @NonNull String content, @NonNull User author, @NonNull Set<String> keywords,
                Set<Category> categories) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.keywords = keywords;
        this.categories = categories;
    }
}
