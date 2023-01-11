package course.hibernate.spring.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("D")
//@PrimaryKeyJoinColumn(name = "debit_acc_id", foreignKey = @ForeignKey(name="fk_debit_acc_id"))
public class DebitAccount extends Account{
    private BigDecimal overdraftFee;

    public DebitAccount() {
    }

    public DebitAccount(Long id, String owner, BigDecimal ballance, BigDecimal interestRate, BigDecimal overdraftFee) {
        super(id, owner, ballance, interestRate);
        this.overdraftFee = overdraftFee;
    }

    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    @Override
    public String toString() {
        return "DebitAccount{" +
                "id=" + getId() +
                ", owner='" + getOwner() + '\'' +
                ", ballance=" + getBallance() +
                ", interestRate=" + getInterestRate() +
                ", overdraftFee=" + overdraftFee +
                '}';
    }
}
