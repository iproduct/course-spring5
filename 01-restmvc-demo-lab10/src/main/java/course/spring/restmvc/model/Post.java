package course.spring.restmvc.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name ="POSTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(generator = "post_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 3
    )
    private Long id;
    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String title;
    @NonNull
    @NotNull
    @Size(min=20, max = 2048)
    private String content;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="AUTHOR_ID", nullable = false, updatable = false, referencedColumnName = "ID")
    @NonNull
    @NotNull
    private User author;

    @ElementCollection
    private List<@Size(min=2, max=15) String> keywords;
    @URL
    private String imageUrl;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();
}
