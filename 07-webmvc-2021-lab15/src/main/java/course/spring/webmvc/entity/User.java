package course.spring.webmvc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Pattern(regexp = "[0-9a-f]{24}")
    private String id;
    @NonNull
    @NotNull
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters long.")
    private String firstName;
    @NonNull
    @NotNull
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters long.")
    private String lastName;
    @NonNull
    @NotNull
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters long.")
    private String username;
    @NonNull
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}")
    private String password;
    private String roles = "READER";
    private boolean active;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();
}

