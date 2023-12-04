package course.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Identifiable<Long> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @Length(min = 2, max = 16)
    private String firstName;
    @NonNull
    @NotNull
    @Length(min = 2, max = 16)
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Set<Role> roles;
    @OneToMany(mappedBy = "author")
    private List<Article> articles = new ArrayList<>();
    private boolean active = true;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();

    public User(@NotNull String firstName, @NonNull @NotNull String lastName, @NotNull String username, @NotNull String password, @NotNull Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
