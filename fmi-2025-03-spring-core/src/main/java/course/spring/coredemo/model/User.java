package course.spring.coredemo.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private Set<Role> role = Set.of(Role.READER);
    private LocalDateTime createdAt =  LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public User(@NonNull String username, @NonNull String password, @NonNull String email, @NonNull String firstName, @NonNull String lastName, Set<Role> role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
