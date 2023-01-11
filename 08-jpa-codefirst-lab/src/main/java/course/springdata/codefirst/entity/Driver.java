package course.springdata.codefirst.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @ManyToMany
    @JoinTable(
            name="drivers_vehicles",
            joinColumns = @JoinColumn(name="driver_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="vehicle_id", referencedColumnName = "id")
    )
    private Set<Vehicle> vehicles = new HashSet<>();

    public Driver() {
    }

    public Driver(String fullName, Set<Vehicle> vehicles) {
        this.fullName = fullName;
        this.vehicles = vehicles;
    }

    public Driver(Long id, String fullName, Set<Vehicle> vehicles) {
        this.id = id;
        this.fullName = fullName;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Driver{");
        sb.append("id=").append(id);
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append(", vehicles=").append(vehicles);
        sb.append('}');
        return sb.toString();
    }
}
