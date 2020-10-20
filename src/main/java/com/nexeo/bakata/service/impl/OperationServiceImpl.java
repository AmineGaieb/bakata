package com.nexeo.bakata.service.impl;

import com.google.common.annotations.VisibleForTesting;
import com.nexeo.bakata.domain.BankAccount;
import com.nexeo.bakata.domain.Operation;
import com.nexeo.bakata.domain.OperationType;
import com.nexeo.bakata.repository.BankAccountRepository;
import com.nexeo.bakata.repository.OperationRepository;
import com.nexeo.bakata.service.OperationService;
import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.service.mapper.AccountDtoMapper;
import com.nexeo.bakata.web.rest.errors.NoSuchAccountException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {


    private final OperationRepository operationRepository;
    private final BankAccountRepository bankAccountRepository;
    private AccountDtoMapper dtoMapper;

    public OperationServiceImpl(OperationRepository operationRepository, BankAccountRepository bankAccountRepository, AccountDtoMapper dtoMapper) {
        this.operationRepository = operationRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * debits the specified amount on the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public AccountDto doWithdrawal(Long accountId, Long amount) throws NoSuchAccountException {
        createAndPerformOperation(accountId, Math.abs(amount), OperationType.WITHDRAWAL);
        BankAccount bankAccount = bankAccountRepository.findById(accountId).get();
        return dtoMapper.mapEntityToDto(bankAccount);
    }


    /**
     * deposit the specified amount into the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public AccountDto doDeposit(Long accountId, Long amount) throws NoSuchAccountException {
        createAndPerformOperation(accountId, Math.abs(amount), OperationType.DEPOSIT);
        BankAccount bankAccount = bankAccountRepository.findById(accountId).get();
        return dtoMapper.mapEntityToDto(bankAccount);
    }


    /**
     * create and perform the specified operation on the given account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @param operationType the transaction type(debit or credit)
     * @return newly created operation
     * @throws NoSuchAccountException
     */
      @VisibleForTesting
      public Operation createAndPerformOperation(Long accountId, Long amount, OperationType operationType) throws NoSuchAccountException {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        if(!optionalBankAccount.isPresent()){
            throw new NoSuchAccountException(": "+accountId);
        }
        BankAccount bankAccount = optionalBankAccount.get();
        int opType = operationType.equals(OperationType.WITHDRAWAL) ? -1 : 1;
        Operation operation = new Operation();
        operation.setAmount(opType * amount);
        operation.setDate(Instant.now());
        operation.setBankAccount(bankAccount);
        operation.setType(operationType);
        bankAccount.balance+=opType*amount;
        operationRepository.save(operation);
        return operation;
    }
}
