package course.hibernate.spring.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Subsystem {
    private String id;
    private String description;
    @Access(AccessType.FIELD)
    @Version
    private Integer version;

    private List<SystemUserEmbeddedId> systemUsers = new ArrayList<>();

    public Subsystem() {
    }

    public Subsystem(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "subsystem", fetch = FetchType.EAGER)
    public List<SystemUserEmbeddedId> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(List<SystemUserEmbeddedId> systemUsers) {
        this.systemUsers = systemUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subsystem)) return false;

        Subsystem subsystem = (Subsystem) o;

        return getId() != null ? getId().equals(subsystem.getId()) : subsystem.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subsystem{");
        sb.append("id='").append(id).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", version=").append(version);
        sb.append(", systemUsers=").append(
                systemUsers.stream().map(SystemUserEmbeddedId::getName).collect(Collectors.joining(", ")));
        sb.append('}');
        return sb.toString();
    }
}
