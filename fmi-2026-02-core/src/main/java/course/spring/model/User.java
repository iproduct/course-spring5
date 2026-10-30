package course.spring.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static course.spring.model.Category.OTHER;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String email;
    @NonNull
    private String name;
    private String address;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public User(@NonNull String email, @NonNull String name, String address) {
        this.email = email;
        this.name = name;
        this.address = address;
    }
}
