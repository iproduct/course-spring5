package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity {
    @NonNull
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Project> projects = new ArrayList<>();

    public Company(Long id, @Size(min = 2, max = 50) String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Company{");
        sb.append("name='").append(name).append('\'');
        sb.append(", projects=").append(projects.stream().map(Project::getName).collect(Collectors.joining()));
        sb.append('}');
        return sb.toString();
    }
}
