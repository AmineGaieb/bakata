package com.nexeo.bakata.service;


import com.nexeo.bakata.domain.BankAccount;
import com.nexeo.bakata.domain.Operation;
import com.nexeo.bakata.domain.OperationType;
import com.nexeo.bakata.repository.BankAccountRepository;
import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.service.impl.BankAccountServiceImpl;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private AccountDtoMapper accountDtoMapper;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private List<Operation> operations;
    private BankAccount account ;
    @Before
    public void setup(){
        account = new BankAccount();
        account.setBalance(5000L);
        account.setId(12L);
        operations = new ArrayList<>();
        operations.add(new Operation(Instant.now(), OperationType.DEPOSIT,10000L,account));
        account.setOperations(operations);
    }


    @Test(expected = NoSuchAccountException.class)
    public void printStatement_throw_exception_for_no_such_account() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());
        bankAccountService.printStatement(12L);
        Assert.fail("should have thrown NoSuchAccountException ");
    }

    @Test
    public void printStatement_return_current_account_balance() throws NoSuchAccountException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountDtoMapper.mapEntityToDto(any(BankAccount.class))).thenCallRealMethod();
        AccountDto accountDto = bankAccountService.printStatement(12L);
        assertThat(accountDto.getBalance()).isEqualTo(account.getBalance());
        assertThat(accountDto.getOperations()).isNotEmpty();
        assertThat(accountDto.getOperations()).hasSameSizeAs(account.getOperations());

        Operation operation = new Operation(Instant.now().minusSeconds(10000), OperationType.DEPOSIT,10000L,account);
        account.getOperations().add(operation);
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        accountDto = bankAccountService.printStatement(12L);
        assertThat(accountDto.getOperations()).hasSize(2);
        assertThat(accountDto.getOperations()).isSortedAccordingTo(Comparator.comparing(Operation::getDate).reversed());

    }




}
