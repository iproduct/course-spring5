package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @NonNull
    @NotNull
    @Size(min=2, max=20)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min=2, max=20)
    private String lastName;

    @NonNull
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NonNull
    @NotNull
    @Size(min=2, max=30)
    @Column(unique = true)
    private String username;

    @NonNull
    @NotNull
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Set.of(Role.EMPLOYEE);

    private boolean active = true;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified = new Date();


    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Project> projects = new HashSet<>();

    public User(@NonNull @Size(min = 2, max = 20) String firstName, @NonNull @Size(min = 2, max = 20) String lastName, @NonNull @Email String email, @NonNull @Size(min = 2, max = 30) String username, @NonNull @Size(min = 5, max = 30) String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && getEmail().equals(user.getEmail()) && getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName(), getEmail(), getUsername());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .collect(Collectors.toList());
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
