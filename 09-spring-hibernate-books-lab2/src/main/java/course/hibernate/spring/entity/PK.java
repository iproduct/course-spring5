package course.hibernate.spring.entity;

import javax.persistence.Id;
import java.io.Serializable;

public class PK implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subsystem;
    private String username;

    public PK() {
    }

    public PK(String subsystem, String username) {
        this.subsystem = subsystem;
        this.username = username;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PK)) return false;

        PK pk = (PK) o;

        if (subsystem != null ? !subsystem.equals(pk.subsystem) : pk.subsystem != null) return false;
        return username != null ? username.equals(pk.username) : pk.username == null;
    }

    @Override
    public int hashCode() {
        int result = subsystem != null ? subsystem.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
