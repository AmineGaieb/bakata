package com.nexeo.bakata.service.dto;

public class OperationCommand {

    private Long amount ;

    public OperationCommand() {
    }

    public OperationCommand(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
