package com.ut.se.messagingjms.service;

import com.ut.se.messagingjms.entity.AccountEntity;
import com.ut.se.messagingjms.lib.consumer.TransactionMessageConsumer;
import com.ut.se.messagingjms.lib.dto.SystemResponseDTO;
import com.ut.se.messagingjms.lib.enums.SystemResponse;
import com.ut.se.messagingjms.lib.publisher.SystemResponsePublisher;
import com.ut.se.messagingjms.repository.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMessageConsumer.class);

    private final AccountsRepository accountsRepository;

    private final SystemResponsePublisher systemResponsePublisher;


    @Autowired
    public TransactionService(AccountsRepository accountRepository, SystemResponsePublisher systemResponsePublisher) {
        this.accountsRepository = accountRepository;
        this.systemResponsePublisher = systemResponsePublisher;
    }

    public void deposit(String accountNo, int amount) throws Exception {
        AccountEntity account = this.accountsRepository.getAccount(accountNo);

        if (account != null) {
            account.setAmount(account.getAmount() + amount);
        } else {
            account = this.accountsRepository.addAccount(accountNo, amount);
        }

        LOGGER.info("deposit account {} with amount {}\\ new amount: {}", accountNo, amount, account.getAmount());

        this.systemResponsePublisher.send(new SystemResponseDTO(
                SystemResponse.Deposit.Success.CODE,
                SystemResponse.Deposit.Success.ACTION,
                SystemResponse.Deposit.Success.CONTEXT
        ));
    }

    public void withdraw(String accountNo, int amount) {
        AccountEntity account = this.accountsRepository.getAccount(accountNo);

        if (account == null) {
            LOGGER.error("cannot withdraw account {}, it is not exists", accountNo);
            this.systemResponsePublisher.send(new SystemResponseDTO(
                    SystemResponse.UnknownAccountNumber.CODE,
                    SystemResponse.UnknownAccountNumber.ACTION,
                    SystemResponse.UnknownAccountNumber.CONTEXT
            ));
            return;
        }

        int accountAmount = account.getAmount();
        if (accountAmount < amount) {
            LOGGER.error("can't withdraw account {} with amount {}, current amount: {}",
                    accountNo, amount, account.getAmount());
            this.systemResponsePublisher.send(new SystemResponseDTO(
                    SystemResponse.Withdraw.Insufficient.CODE,
                    SystemResponse.Withdraw.Insufficient.ACTION,
                    SystemResponse.Withdraw.Insufficient.CONTEXT
            ));
            return;
        }

        account.setAmount(accountAmount - amount);

        LOGGER.info("withdraw account {} with amount {}\\ new amount: {}", accountNo, amount, account.getAmount());

        this.systemResponsePublisher.send(new SystemResponseDTO(
                SystemResponse.Withdraw.Success.CODE,
                SystemResponse.Withdraw.Success.ACTION,
                SystemResponse.Withdraw.Success.CONTEXT
        ));
    }

    public void balance(String accountNo) {
        AccountEntity account = this.accountsRepository.getAccount(accountNo);

        if (account == null) {
            LOGGER.error("can't balance account {}", accountNo);
            this.systemResponsePublisher.send(new SystemResponseDTO(
                    SystemResponse.UnknownAccountNumber.CODE,
                    SystemResponse.UnknownAccountNumber.ACTION,
                    SystemResponse.UnknownAccountNumber.CONTEXT
            ));
            return;
        }

        LOGGER.info("balance account {}", accountNo);
        this.systemResponsePublisher.send(new SystemResponseDTO(
                SystemResponse.Balance.Success.CODE,
                SystemResponse.Balance.Success.ACTION,
                Integer.toString(account.getAmount())
        ));
    }
}
