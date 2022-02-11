package ws.spring.model;

import lombok.*;
import ws.spring.dao.Identifiable;

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

}
