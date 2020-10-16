package course.spring.restmvc.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static course.spring.restmvc.model.Role.READER;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 3
    )
    private Long id;
    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String firstName;
    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String lastName;
    @NonNull
    @NotNull
    @Email
    private String email;
    @NonNull
    @NotNull
    @Size(min=5, max = 30)
    private String username;
    @NonNull
    @NotNull
    @Size(min=5, max = 30)
    private String password;
    private boolean active = true;
    @ElementCollection
    private Set<Role> roles = new HashSet(Arrays.asList(new Role[]{READER}));
    @URL
    @Basic(optional = true)
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    @OneToMany(targetEntity = Post.class, mappedBy = "author", fetch = FetchType.LAZY)
    List<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
