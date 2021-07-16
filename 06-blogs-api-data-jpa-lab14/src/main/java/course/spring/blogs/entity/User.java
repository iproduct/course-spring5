package course.spring.blogs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @SequenceGenerator(name="userSeqGen", sequenceName = "userSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    @NotNull
    @Size(min=1, max=30)
    private String firstName;
    @NonNull
    @NotNull
    @Size(min=1, max=30)
    private String lastName;
    @Basic(optional = false)
    @Column(unique = true, nullable = false, length = 20)
    @NonNull
    @NotNull
    @Size(min=4, max=30)
    private String username;
    @NonNull
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "password must be at least 8 characters with at least 1 digit")
    private String password;
    @NonNull
    @NotNull
    @Email
    private String email;
    private boolean active = true;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}
