package course.spring.restmvc.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;

import static course.spring.restmvc.model.Role.READER;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
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
    private String imageUrl;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
