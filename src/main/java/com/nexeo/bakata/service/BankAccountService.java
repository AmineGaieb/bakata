package com.nexeo.bakata.service;

import com.nexeo.bakata.service.dto.AccountDto;
import com.nexeo.bakata.web.rest.errors.NoSuchAccountException;

public interface BankAccountService {

    AccountDto printStatement(Long accountId) throws NoSuchAccountException;
}
