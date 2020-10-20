package com.nexeo.bakata.web.rest;

import com.nexeo.bakata.service.BankAccountService;
import com.nexeo.bakata.service.OperationService;
import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.service.dto.OperationCommand;
import com.nexeo.bakata.web.rest.errors.NoSuchAccountException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/accounts/")
public class BankAccountResource {

    private final BankAccountService bankAccountService;
    private final OperationService operationService;

    public BankAccountResource(BankAccountService bankAccountService, OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @ApiOperation(value = "History of operations")
    @GetMapping("{accountId}/statement")
    public AccountDto printAccountState(@PathVariable Long accountId) throws NoSuchAccountException {
        return bankAccountService.printStatement(accountId);
    }

    @ApiOperation(value = "Deposit money")
    @PostMapping(value = "{accountId}/deposit")
    public AccountDto deposit(@PathVariable Long accountId, @RequestBody OperationCommand operationCommand) throws NoSuchAccountException {
        return operationService.doDeposit(accountId, operationCommand.getAmount());
    }


    @ApiOperation(value = "Withdraw money")
    @PostMapping(value = "{accountId}/withdrawal")
    public AccountDto withdrawal(@PathVariable Long accountId, @RequestBody OperationCommand operationCommand) throws NoSuchAccountException {
        return operationService.doWithdrawal(accountId,operationCommand.getAmount());
    }
}
