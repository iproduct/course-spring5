package course.hibernate.spring.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(PK.class)
public class SystemUser {
    @Id
    private String subsystem;
    @Id
    private String username;

    private String name;

    public SystemUser() {
    }

    public SystemUser(String subsystem, String username, String name) {
        this.subsystem = subsystem;
        this.username = username;
        this.name = name;
    }

    public PK getId() {
        return new PK(subsystem, username);
    }
    public void setId(PK key) {
        subsystem = key.getSubsystem();
        username = key.getUsername();
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemUser)) return false;

        SystemUser that = (SystemUser) o;

        if (getSubsystem() != null ? !getId().equals(that.getId()) : that.getId() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SystemUser{");
        sb.append("subsystem='").append(subsystem).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
