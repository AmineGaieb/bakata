package com.nexeo.bakata.service;

import com.nexeo.bakata.domain.BankAccount;
import com.nexeo.bakata.domain.Operation;
import com.nexeo.bakata.domain.OperationType;
import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.service.mapper.AccountDtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AccountDtoMapperTest {

    private AccountDtoMapper dtoMapper;

    private BankAccount account;

    @Before
    public void setup(){
        dtoMapper = new AccountDtoMapper();
        account = new BankAccount();
        account.setId(12L);
        account.setBalance(50000L);
        account.setOperations(new ArrayList<>());
        for(int i =0;i<10;i++) {
            Operation operation = new Operation(
                    Instant.now().minusSeconds(i), (i % 2 == 0) ? OperationType.DEPOSIT : OperationType.WITHDRAWAL, 10000L, account);

            account.getOperations().add(operation);
        }
    }

    @Test
    public void mapEntityToDto_should_return_account_overview(){
        AccountDto accountDto = dtoMapper.mapEntityToDto(account);
        assertThat(accountDto.getBalance()).isEqualTo(account.getBalance());
        assertThat(accountDto.getOperations().size()).isLessThanOrEqualTo(account.getOperations().size());
        assertThat(accountDto.getOperations()).isSortedAccordingTo(Comparator.comparing(Operation::getDate).reversed());
    }

}

