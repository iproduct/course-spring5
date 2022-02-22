package course.ws.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.print.DocFlavor;
import java.util.HashSet;
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
    private Set<Role> roles;
    private boolean active = true;
}
