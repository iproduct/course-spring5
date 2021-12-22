package course.spring.restjpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;


@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
//@JsonPropertyOrder(alphabetic=true)
@Data
public class User implements UserDetails {
//    @Id
    private long id;

    @NotNull
    @Length(min = 3, max = 30)
    @NonNull
    private String username;

    @NotNull
    @Length(min = 4, max = 80)
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

    @NonNull
    @Builder.Default
    private String roles;

    @Builder.Default
    private boolean active = true;

    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime created = LocalDateTime.now();

    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updated = LocalDateTime.now();

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
        this.updated = updated;
    }

    public User() {
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
    public String getPassword() {
        return password;
    }

    @JsonProperty(access=WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(roles.split("\\s*,\\s*")).stream()
                .map(rolename -> "ROLE_" + rolename)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
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
