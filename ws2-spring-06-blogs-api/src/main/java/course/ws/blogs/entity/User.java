package course.ws.blogs.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users", indexes = @Index(name = "idx_user_email", columnList = "email", unique = true))
//   uniqueConstraints = @UniqueConstraint(name = "uc_user_email", columnNames = "email"))
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    @Size(min=2, max=50)
    private String lastName;
    @NotBlank
    @Email
//    @Column(unique = true)
    private String email;
    @NotBlank
    @Size(min=8)
    private String password;
    @NotNull
    private Role role = Role.READER;
    private boolean active = true;
    @PastOrPresent
    private LocalDateTime created = LocalDateTime.now();
    @PastOrPresent
    private LocalDateTime modified = LocalDateTime.now();

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
