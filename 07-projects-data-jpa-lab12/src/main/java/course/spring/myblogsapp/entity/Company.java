package course.spring.myblogsapp.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity{
    @NonNull
    @NotNull
    @Size(min=2, max=60)
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Project> projects = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        if (!getName().equals(company.getName())) return false;
        return getProjects() != null ? getProjects().equals(company.getProjects()) : company.getProjects() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
