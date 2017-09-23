package de.diedavids.cuba.example.ceem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s - %s|customerName,amount")
@Table(name = "CEEM_PRESENT")
@Entity(name = "ceem$Present")
public class Present extends StandardEntity {
    private static final long serialVersionUID = -2734379746790655241L;

    @NotNull
    @Column(name = "CUSTOMER_NAME", nullable = false)
    protected String customerName;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    protected Integer amount;

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }


}