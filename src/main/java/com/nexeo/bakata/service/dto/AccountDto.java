package com.nexeo.bakata.service.dto;

import com.nexeo.bakata.domain.Operation;

import java.util.List;

public class AccountDto {

    private Long balance;

    private List<Operation> operations;

    public AccountDto() {
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
