package course.spring.blogs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import course.spring.blogs.entity.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static course.spring.blogs.entity.Role.AUTHOR;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserCreateDto {
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
    @JsonProperty(access = WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "password must be at least 8 characters with at least 1 digit")
    private String password;
    @NonNull
    @NotNull
    @Email
    private String email;
    @NonNull
    private Role role = AUTHOR;
    private boolean active = true;

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public UserCreateDto(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, @NonNull String email, @NonNull Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
