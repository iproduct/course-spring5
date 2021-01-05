package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @NotNull
    @Size(min = 2, max = 80)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 80)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 2, max = 80)
    private String username;

    @NotNull
    @Size(min = 2, max = 30)
    private String password;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate = new Date();

    private boolean active = true;

    @ManyToOne
    private Project project;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name="user_id", , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(@Size(min = 2, max = 80) String firstName, @Size(min = 2, max = 80) String lastName, @Email String email, @Size(min = 2, max = 80) String username, @Size(min = 2, max = 30) String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
