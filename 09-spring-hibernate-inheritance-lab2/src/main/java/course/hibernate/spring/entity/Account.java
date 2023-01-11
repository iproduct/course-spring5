package course.hibernate.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "acc_type", discriminatorType = DiscriminatorType.STRING, length = 1)
//@DiscriminatorFormula("SUBSTRING(id, 1, 1)")
//@DiscriminatorValue("A")
//@DiscriminatorOptions(force = false, insert = true)
public class Account {
    @Id
    private Long id;
    private String owner;
    private BigDecimal ballance;
    private BigDecimal interestRate;
}
