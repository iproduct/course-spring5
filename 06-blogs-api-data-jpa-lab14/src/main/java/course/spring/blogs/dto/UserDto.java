package course.spring.blogs.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import course.spring.blogs.entity.Post;
import course.spring.blogs.entity.Role;
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

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    private Long id;
    @NonNull
    @NotNull
    @Size(min=1, max=30)
    private String firstName;
    @NonNull
    @NotNull
    @Size(min=1, max=30)
    private String lastName;
    @NonNull
    @NotNull
    @Size(min=4, max=30)
    private String username;
    @NonNull
    @NotNull
    @Email
    private String email;
    private Role role = AUTHOR;
    private boolean active = true;

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}
