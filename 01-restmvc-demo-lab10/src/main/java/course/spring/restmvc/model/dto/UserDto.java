package course.spring.restmvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import course.spring.restmvc.model.Role;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static course.spring.restmvc.model.Role.READER;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"posts", "authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class UserDto implements UserDetails {
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
    @Size(min=3, max = 30)
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    @NonNull
    @NotNull
    @Size(min=5, max = 30)
    private String password;

    private boolean active = true;

    private Set<Role> roles = new HashSet(Arrays.asList(new Role[]{READER}));

    @URL
    private String imageUrl;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();


    @ToString.Exclude
    List<PostDto> posts = new ArrayList<>();

    public UserDto(@NonNull @NotNull @Size(min = 2, max = 50) String firstName, @NonNull @NotNull @Size(min = 2, max = 50) String lastName, @NonNull @NotNull @Email String email, @NonNull @NotNull @Size(min = 5, max = 30) String username, @NonNull @NotNull @Size(min = 5, max = 30) String password, Set<Role> roles, @URL String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.imageUrl = imageUrl;
    }

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
