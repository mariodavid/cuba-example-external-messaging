package de.diedavids.cuba.example.ceem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("ceem_CustomerEntityListener")
@NamePattern("%s|name")
@Table(name = "CEEM_CUSTOMER")
@Entity(name = "ceem$Customer")
public class Customer extends StandardEntity {
    private static final long serialVersionUID = -3502650609093767249L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}