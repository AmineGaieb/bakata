package com.nexeo.bakata.service.mapper;

import com.nexeo.bakata.domain.BankAccount;
import com.nexeo.bakata.domain.Operation;
import com.nexeo.bakata.service.dto.AccountDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDtoMapper {

    public AccountDto mapEntityToDto(BankAccount bankAccount){
        AccountDto dto = new AccountDto();
        dto.setBalance(bankAccount.getBalance());
        List<Operation> operations = bankAccount.getOperations()
                .stream()
                .sorted(Comparator.comparing(Operation::getDate).reversed()).collect(Collectors.toList());
        dto.setOperations(operations);
        return dto;
    }
}
