package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Access(AccessType.FIELD)
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity  implements UserDetails {
    @NonNull
    @NotNull
    @Size(min = 2, max = 80)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 2, max = 80)
    private String lastName;

    @NonNull
    @NotNull
    @Email
    private String email;

    @NonNull
    @NotNull
    @Size(min = 2, max = 80)
    private String username;

    @NonNull
    @NotNull
//    @Size(min = 2, max = 30)
    private String password;

    @NonNull
    @URL
    private String imageUrl;

    private boolean active = true;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<Post> posts = new ArrayList<>();

    @ElementCollection
    private Set<Role> roles = Set.of(Role.READER);

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public User(@NonNull @Size(min = 2, max = 80) String firstName, @NonNull @Size(min = 2, max = 80) String lastName, @NonNull @Email String email, @NonNull @Size(min = 2, max = 80) String username, @NonNull @Size(min = 2, max = 30) String password, @NonNull @URL String imageUrl, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
        this.roles = roles;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .collect(Collectors.toList());
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return active;
    }
}
