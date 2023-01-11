package course.hibernate.spring.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class EntityBase {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "custom-id"
    )
    @GenericGenerator(
            name="custom-id",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="sequence_name", value="user_sequence"),
                    @org.hibernate.annotations.Parameter(name="initial_value", value="1"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="5"),
                    @Parameter(name="optimizer", value="pooled")
            }
    )
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "base_table_generator")
//    @TableGenerator(
//            name = "base_table_generator",
//            table= "generated_values",
//            pkColumnName = "entity_name",
//            valueColumnName = "last_id",
//            allocationSize = 5
//    )
    private Long id;

    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public EntityBase() {
    }

    public EntityBase(Long id) {
        this.id = id;
    }

    public EntityBase(Long id, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.created = created;
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityBase)) return false;

        EntityBase that = (EntityBase) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
