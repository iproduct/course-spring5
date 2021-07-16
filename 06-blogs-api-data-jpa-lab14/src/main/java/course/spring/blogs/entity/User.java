package course.spring.blogs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static course.spring.blogs.entity.Role.AUTHOR;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {
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
    @JsonProperty(access = WRITE_ONLY)
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
//            message = "password must be at least 8 characters with at least 1 digit")
    private String password;
    @NonNull
    @NotNull
    @Email
    private String email;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role = AUTHOR;
    private boolean active = true;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, @NonNull String email, @NonNull Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getRole().toString()));
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
