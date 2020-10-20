package com.nexeo.bakata.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    public Long balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    public List<Operation> operations;

    public Long getId() {
        return id;
    }

    public BankAccount() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

}
