package course.spring.core.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements Identifiable<Long> {
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private Role role;

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();
}
