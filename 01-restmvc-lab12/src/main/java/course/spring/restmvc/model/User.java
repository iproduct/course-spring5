package course.spring.restmvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Document(collection="posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Pattern(regexp = "[A-Za-z0-9]{24}") // Mongodb ObjectID
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull
    @Size(min=6)
    private String password;

    @NonNull
    @URL
    private String imageUrl;
    private Set<Role> keywords = Set.of();
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

}
