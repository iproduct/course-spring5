package course.spring.restjpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import course.spring.restjpa.entity.Article;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
//@JsonPropertyOrder(alphabetic=true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 5, max = 20)
    @NonNull
    private String username;

    @NotNull
    @NonNull
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @NotNull
    @Length(min = 1, max = 30)
    @NonNull
    private String fname;

    @NotNull
    @Length(min = 1, max = 30)
    @NonNull
    private String lname;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    @ToString.Exclude
    private List<Article> articles = new ArrayList<>();

    private String roles = "AUTHOR";

    private boolean active = true;

    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime modified = LocalDateTime.now();

    public User(long id,
                @NotNull @Length(min = 3, max = 30) String username,
                @NotNull @Length(min = 4, max = 80) String password,
                @NotNull @Length(min = 1, max = 30) String fname,
                @NotNull @Length(min = 1, max = 30) String lname,
                String roles,
                boolean active,
                LocalDateTime created,
                LocalDateTime updated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
        this.active = active;
        this.created = created;
        this.modified = updated;
    }

    @java.beans.ConstructorProperties({"username", "password", "fname", "lname", "roles"})
    public User(@NotNull @Length(min = 3, max = 30) String username,
                @NotNull @Length(min = 4, max = 80) String password,
                @NotNull @Length(min = 1, max = 30) String fname,
                @NotNull @Length(min = 1, max = 30) String lname,
                String roles) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(roles.split("\\s*,\\s*")).stream()
                .map(rolename -> "ROLE_" + rolename)
                .map(SimpleGrantedAuthority::new)
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
