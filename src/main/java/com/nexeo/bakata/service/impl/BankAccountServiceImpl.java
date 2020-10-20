package com.nexeo.bakata.service.impl;

import com.nexeo.bakata.domain.BankAccount;
import com.nexeo.bakata.repository.BankAccountRepository;
import com.nexeo.bakata.service.BankAccountService;
import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.service.mapper.AccountDtoMapper;
import com.nexeo.bakata.web.rest.errors.NoSuchAccountException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final AccountDtoMapper accountDtoMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, AccountDtoMapper accountDtoMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountDtoMapper = accountDtoMapper;
    }

    /**
     *
     * @param accountId account identifier
     * @return  the account state including operations
     * @throws NoSuchAccountException
     */
    public AccountDto printStatement(Long accountId) throws NoSuchAccountException {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        if(!optionalBankAccount.isPresent()){
            throw new NoSuchAccountException(": "+accountId);
        }
        return accountDtoMapper.mapEntityToDto(optionalBankAccount.get());
    }
}
