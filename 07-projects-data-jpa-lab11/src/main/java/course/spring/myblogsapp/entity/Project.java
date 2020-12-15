package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {
    @NotNull
    @Size(min = 2, max = 80)
    private String name;

    @NotNull
    @Size(min = 2, max = 1024)
    private String description;

    @NotNull
    @Positive
    private double budget;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate = new Date();

    private boolean finished = false;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "project")
    private List<User> employees = new ArrayList<>();

    public Project(@Size(min = 2, max = 80) String name, @Size(min = 2, max = 1024) String description, @Positive double budget, Company company) {
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.company = company;
    }

    public Project(Long id, @Size(min = 2, max = 80) String name, @Size(min = 2, max = 1024) String description, @Positive double budget, Company company) {
        super(id);
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.company = company;
    }

    public Project(Long id, @Size(min = 2, max = 80) String name, @Size(min = 2, max = 1024) String description, @Positive double budget, Date startDate, boolean finished, Company company) {
        super(id);
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.startDate = startDate;
        this.finished = finished;
        this.company = company;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", budget=").append(budget);
        sb.append(", startDate=").append(startDate);
        sb.append(", finished=").append(finished);
        sb.append(", company=").append(company.getName());
        sb.append(", employees=").append(employees);
        sb.append('}');
        return sb.toString();
    }
}
