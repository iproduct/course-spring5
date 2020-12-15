package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @NotNull
    @Size(min = 2, max = 80)
    private String authority;

    @ManyToMany(mappedBy = "roles", targetEntity = User.class)
    private Set<User> users = new HashSet<>();
}
