package course.hibernate.spring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class City implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
}
