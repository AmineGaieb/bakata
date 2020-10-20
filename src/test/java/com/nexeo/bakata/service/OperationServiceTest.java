package com.nexeo.bakata.service;


import com.nexeo.bakata.domain.BankAccount;
import com.nexeo.bakata.domain.Operation;
import com.nexeo.bakata.domain.OperationType;
import com.nexeo.bakata.repository.BankAccountRepository;
import com.nexeo.bakata.repository.OperationRepository;
import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.service.impl.OperationServiceImpl;
import com.nexeo.bakata.service.mapper.AccountDtoMapper;
import com.nexeo.bakata.web.rest.errors.NoSuchAccountException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class OperationServiceTest {


    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountDtoMapper accountDtoMapper;

    @InjectMocks
    private OperationServiceImpl operationService;

    private BankAccount account ;
    private Operation operation;
    @Before
    public void setUp(){
        account = new BankAccount();
        account.setBalance(5000L);
        account.setId(12L);
        account.setOperations(new ArrayList<>());
        operation = new Operation(Instant.now(), OperationType.DEPOSIT,10000L, null);
    }

    @Test(expected = NoSuchAccountException.class)
    public void createAndPerformOperation_throw_NoSuchAccountException() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());
        operationService.createAndPerformOperation(12L,0L,OperationType.WITHDRAWAL);
        Assert.fail("should have thrown NoSuchAccountException ");

    }

    @Test
    public void createAndPerformOperation_perform_deposit() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        long currentAccountBalance = account.getBalance();
        Operation operation = operationService.createAndPerformOperation(12L,1000L,OperationType.DEPOSIT);
        assertThat(operation.getAmount()).isEqualTo(1000);
        assertThat(operation.getType()).isEqualTo(OperationType.DEPOSIT);
        assertThat(operation.getBankAccount()).isNotNull();
        assertThat(operation.getBankAccount().getBalance()).isEqualTo(currentAccountBalance+1000);
    }

    @Test
    public void createAndPerformOperation_perform_withdrawal() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        long currentAccountBalance = account.getBalance();
        Operation operation = operationService.createAndPerformOperation(12L,5000L,OperationType.WITHDRAWAL);
        assertThat(operation.getAmount()).isEqualTo(-5000);
        assertThat(operation.getType()).isEqualTo(OperationType.WITHDRAWAL);
        assertThat(operation.getBankAccount()).isNotNull();
        assertThat(operation.getBankAccount().getBalance()).isEqualTo(currentAccountBalance-5000);
    }

    @Test(expected = NoSuchAccountException.class)
    public void doDeposit_should_throw_NoSuchAccountException() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());
        operationService.doDeposit(12L,1200L);
        Assert.fail("should have thrown NoSuchAccountException ");
    }


    @Test
    public void doDeposit_perform_deposit_and_save_operation() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountDtoMapper.mapEntityToDto(any(BankAccount.class))).thenCallRealMethod();
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        long currentAccountBalance = account.getBalance();
        AccountDto dto = operationService.doDeposit(12L,1200L);
        assertThat(dto.getOperations().size()).isEqualTo(1);
        assertThat(dto.getOperations().get(0).getType()).isEqualTo(OperationType.DEPOSIT);
        assertThat(dto.getOperations().get(0).getAmount()).isEqualTo(1200);
        assertThat(dto.getBalance()).isEqualTo(currentAccountBalance+1200);
    }

    @Test(expected = NoSuchAccountException.class)
    public void doWithdrawal_throw_NoSuchAccountException() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());
        operationService.doWithdrawal(12L,1200L);
        Assert.fail("should have thrown NoSuchAccountException ");
    }

    @Test
    public void doWithdrawal_perform_withdrawal_and_save_operation() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountDtoMapper.mapEntityToDto(any(BankAccount.class))).thenCallRealMethod();
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);
        long currentAccountBalance = account.getBalance();
        AccountDto dto = operationService.doWithdrawal(12L,1200L);
        assertThat(dto.getOperations().size()).isEqualTo(1);
        assertThat(dto.getOperations().get(0).getType()).isEqualTo(OperationType.WITHDRAWAL);
        assertThat(dto.getOperations().get(0).getAmount()).isEqualTo(-1200);
        assertThat(dto.getBalance()).isEqualTo(currentAccountBalance-1200);
    }

}
