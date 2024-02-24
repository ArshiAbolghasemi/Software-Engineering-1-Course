package com.ut.se.messagingjms.repository;

import com.ut.se.messagingjms.entity.AccountEntity;
import com.ut.se.messagingjms.lib.consumer.TransactionMessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class AccountsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMessageConsumer.class);

    private final ArrayList<AccountEntity> accountEntities;

    public AccountsRepository() {
        this.accountEntities = new ArrayList<>();
    }

    @Nullable
    public AccountEntity getAccount(String accountNo) {
        LOGGER.info("fetching account {}", accountNo);
        Optional<AccountEntity> account = this.accountEntities.stream()
                .filter(accountEntity -> accountEntity.getAccountNo().equals(accountNo))
                .findFirst();

        return account.orElse(null);
    }

    public AccountEntity addAccount(String accountNo, int amount) throws Exception {
        if (this.getAccount(accountNo) != null) {
            LOGGER.error("account {} doesn't exist", accountNo);
            throw new Exception(String.format("account %s is already exists", accountNo));
        };

        AccountEntity accountEntity = new AccountEntity(accountNo, amount);
        this.accountEntities.add(accountEntity);

        LOGGER.info("adding account {} with amount {}", accountNo, amount);
        return accountEntity;
    }
}
