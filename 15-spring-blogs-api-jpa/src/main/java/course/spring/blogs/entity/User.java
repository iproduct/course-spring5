package course.spring.blogs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Table(name="USERS", uniqueConstraints = {
//        @UniqueConstraint(name="UC_USERNAME", columnNames={"USERNAME"}),
//        @UniqueConstraint(name="UC_FIST_LAST_NAME", columnNames={"FIRST_NAME", "LAST_NAME"})
//})
@Table(name="USERS", indexes = {
        @Index(name = "UC_USERNAME", columnList = "USERNAME", unique = true),
        @Index(name = "UC_NAMES", columnList = "FIRST_NAME,LAST_NAME")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @Size(min=2, max=30)
    @NonNull
    @Column(name="FIRST_NAME")
    private String firstName;
    @NotNull
    @Size(min=2, max=30)
    @NonNull
    @Column(name="LAST_NAME")
    private String lastName;
    @NotNull
    @Size(min=2, max=30)
    @NonNull
    private String username;
    @NotNull
    @Size(min=8)
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role = Role.READER;
    @URL
    private String imageUrl;
    private boolean active = true;
    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    @JsonIgnore
    private List<Article> articles = List.of();
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    public User() {
    }

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, @NonNull Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return active;
    }
}
