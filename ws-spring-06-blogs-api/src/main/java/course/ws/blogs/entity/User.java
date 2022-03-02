package course.ws.blogs.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Size(min=2, max=50)
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @NotNull
    @Size(min=5, max=20)
    private String username;
    @NotNull
    @Size(min=8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.READER;

    private boolean active = true;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();

    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss", timezone="Europe/Sofia")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss", timezone="Europe/Sofia")
    private LocalDateTime modified = LocalDateTime.now();

    public User(String firstName, String lastName, String username, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getRole()));
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















