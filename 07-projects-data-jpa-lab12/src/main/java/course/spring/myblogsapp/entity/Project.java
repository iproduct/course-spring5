package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {
    @NonNull
    @NotNull
    @Size(min = 2, max = 60)
    private String name;

    @NonNull
    @NotNull
    @Size(min = 2, max = 1024)
    private String description;

    @NonNull
    @NotNull
    @Positive
    private double budget;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.DATE)
    private Date startDate = new Date();

    private boolean finished = false;

    @NotNull
    @ManyToOne
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "projects_users",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> users = new HashSet<>();

    public Project(@NonNull @Size(min = 2, max = 60) String name, @NonNull @Size(min = 2, max = 1024) String description, @NonNull @Positive double budget, Company company) {
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        if (!super.equals(o)) return false;
        Project project = (Project) o;
        return getName().equals(project.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", budget=").append(budget);
        sb.append(", startDate=").append(startDate);
        sb.append(", finished=").append(finished);
        sb.append(", company=").append(company.getName());
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}
