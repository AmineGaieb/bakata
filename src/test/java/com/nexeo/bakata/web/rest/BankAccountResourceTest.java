package com.nexeo.bakata.web.rest;


import com.nexeo.bakata.BakataApplication;
import com.nexeo.bakata.domain.BankAccount;
import com.nexeo.bakata.repository.BankAccountRepository;
import com.nexeo.bakata.service.BankAccountService;
import com.nexeo.bakata.service.OperationService;
import com.nexeo.bakata.service.dto.OperationCommand;
import com.nexeo.bakata.web.rest.BankAccountResource;
import com.nexeo.bakata.web.rest.errors.GlobalErrorHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static com.nexeo.bakata.util.TestUtil.convertObjectToJsonBytes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BakataApplication.class)
public class BankAccountResourceTest {
    @Autowired
    private OperationService operationService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private GlobalErrorHandler globalErrorHandler;

    private MockMvc restMvc;


    @Before
    public void setup() {
        BankAccountResource bankAccountResource = new BankAccountResource(bankAccountService, operationService);
        this.restMvc = MockMvcBuilders.standaloneSetup(bankAccountResource).setControllerAdvice(globalErrorHandler)
                .build();
    }

    @Test
    public void printAccountState_error_400_code_status() throws Exception {
        restMvc.perform(get("/api/accounts/2455111/statement")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void printAccountState_return_account_details() throws Exception {
        BankAccount account = new BankAccount();
        account.setBalance(1000L);
        account.setOperations(new ArrayList<>());
        bankAccountRepository.saveAndFlush(account);
        restMvc.perform(get("/api/accounts/{id}/statement", account.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.operations").isEmpty())
                .andExpect(jsonPath("$.balance").value(account.getBalance()));
    }

    @Test
    public void deposit_return_error_message_and_400_code_status() throws Exception {

         restMvc.perform(put("/api/accounts/2222/deposit")
                .accept(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(new OperationCommand(2522L))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void deposit_perform_a_deposit_operation() throws Exception {
        BankAccount account = new BankAccount();
        account.setBalance(0L);
        account.setOperations(new ArrayList<>());
        bankAccountRepository.saveAndFlush(account);
        restMvc.perform(put("/api/accounts/{accountId}/deposit", account.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(new OperationCommand(15000L))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operations").isNotEmpty())
                .andExpect(jsonPath("$.balance").value(15000L));

    }

    @Test
    public void withdrawal_should_return_error_message_and_400_code_status() throws Exception {
        restMvc.perform(put("/api/accounts/121212/withdrawal")
                .accept(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(new OperationCommand(2522L))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void withdrawal_perform_a_withdrawal_operation() throws Exception {
        BankAccount account = new BankAccount();
        account.setBalance(0L);
        account.setOperations(new ArrayList<>());
        bankAccountRepository.saveAndFlush(account);
        restMvc.perform(put("/api/accounts/{accountId}/withdrawal", account.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(new OperationCommand(200L))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operations").isNotEmpty())
                .andExpect(jsonPath("$.balance").value(-200L));
    }

}