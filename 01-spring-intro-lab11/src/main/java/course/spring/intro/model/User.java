package course.spring.intro.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.print.DocFlavor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Pattern(regexp = "^[A-Fa-f0-9]{24}$")
    private String id;
    @NonNull
    @NotNull
    @Size(min=2, max=30)
    private String firstName;
    @NonNull
    @NotNull
    @Size(min=2, max=30)
    private String lastName;
    @NonNull
    @NotNull
    @Size(min=2, max=30)
    private String username;
    @NonNull
    @NotNull
    @Size(min=6, max=30)
    private String password;
    @NonNull
    @URL
    private String imageUrl;
    private Set<Role> roles = Set.of(Role.READER);
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public User(@NonNull @NotNull @Size(min = 2, max = 30) String firstName, @NonNull @NotNull @Size(min = 2, max = 30) String lastName,
                @NonNull @NotNull @Size(min = 2, max = 30) String username, @NonNull @NotNull @Size(min = 6, max = 30) String password,
                @NonNull @URL String imageUrl, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
        this.roles = roles;
    }
}
