package course.hibernate.spring.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("C")
//@PrimaryKeyJoinColumn(name = "credit_acc_id", foreignKey = @ForeignKey(name="fk_credit_acc_id"))
public class CreditAccount extends Account{
    private BigDecimal creditLimit;

    public CreditAccount() {
    }

    public CreditAccount(Long id, String owner, BigDecimal ballance, BigDecimal interestRate, BigDecimal creditLimit) {
        super(id, owner, ballance, interestRate);
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "id=" + getId() +
                ", owner='" + getOwner() + '\'' +
                ", ballance=" + getBallance() +
                ", interestRate=" + getInterestRate() +
                ", creditLimit=" + creditLimit +
                '}';
    }
}
