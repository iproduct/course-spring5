package course.ws.blogs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstName;
    @NotNull
    @Size(min=2, max=50)
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + getRole()));
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















