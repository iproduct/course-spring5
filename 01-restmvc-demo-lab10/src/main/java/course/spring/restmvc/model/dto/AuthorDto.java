package course.spring.restmvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import course.spring.restmvc.model.Role;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class AuthorDto {
    private Long id;

    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min=2, max = 50)
    private String lastName;

    @URL
    private String imageUrl;

    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();


    @ToString.Exclude
    List<PostDto> posts = new ArrayList<>();

    public AuthorDto(@NonNull @NotNull @Size(min = 2, max = 50) String firstName, @NonNull @NotNull @Size(min = 2, max = 50) String lastName, @URL String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

}
