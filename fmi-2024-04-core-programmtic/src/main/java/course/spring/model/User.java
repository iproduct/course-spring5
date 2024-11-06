package course.spring.model;

import course.spring.dao.Identifiable;
import lombok.*;

import java.util.Collections;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Identifiable<Long> {
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String fname;
    @NonNull
    private String lname;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private Set<Role> roles = Set.of(Role.READER);

    public User(@NonNull String fname, @NonNull String lname, @NonNull String username, @NonNull String password, Set<Role> roles) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
