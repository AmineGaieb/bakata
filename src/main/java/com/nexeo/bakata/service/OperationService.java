package com.nexeo.bakata.service;

import com.nexeo.bakata.domain.Operation;
import com.nexeo.bakata.domain.OperationType;
import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.web.rest.errors.NoSuchAccountException;

public interface OperationService {

    AccountDto doWithdrawal(Long accountId, Long amount) throws NoSuchAccountException;

    AccountDto doDeposit(Long accountId, Long amount) throws NoSuchAccountException;

    Operation createAndPerformOperation(Long accountId, Long amount, OperationType operationType) throws NoSuchAccountException;
}
