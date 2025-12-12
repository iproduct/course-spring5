package course.spring.intro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;
    @NonNull
    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;
    @NonNull
    @NotBlank
    @Size(min = 5, max = 60)
    private String email;
    @NonNull
    @NotBlank
    @Size(min = 2, max = 40)
    private String username;
    @NonNull
    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "USER_ROLE", joinColumns =
        @JoinColumn(name = "UID", referencedColumnName = "ID")
    )
    private Set<Role> roles = Set.of(Role.READER);
    private boolean enabled = true;
    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @JsonIgnore
    private List<Article> articles =  new ArrayList<>();
    LocalDateTime created = LocalDateTime.now();
    LocalDateTime modified = LocalDateTime.now();

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String username, @NonNull String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
