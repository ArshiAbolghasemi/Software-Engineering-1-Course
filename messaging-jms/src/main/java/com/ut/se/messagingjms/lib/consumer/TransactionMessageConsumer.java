package com.ut.se.messagingjms.lib.consumer;

import com.ut.se.messagingjms.config.JmsConfig;
import com.ut.se.messagingjms.lib.dto.TransactionMessageDTO;
import com.ut.se.messagingjms.lib.enums.TransactionCmd;
import com.ut.se.messagingjms.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

@Component
public class TransactionMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMessageConsumer.class);

    private final TransactionService transactionService;

    @Autowired
    public TransactionMessageConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @JmsListener(destination = JmsConfig.INPUT_QUEUE_NAME, containerFactory = "UTMessagingJmsListenerContainerFactory",
            concurrency = "1")
    public void receive(TransactionMessageDTO dto) {
        LOGGER.info("received cmd: {}", dto);
        String[] args = dto.getArgs();
        try {
            switch (dto.getCommand()) {
                case TransactionCmd.Deposit.CMD:
                    if (!TransactionCmd.Deposit.isValidArgsStructure(args)) {
                        throw new IllegalArgumentException("Invalid Deposit cmd arguments!");
                    }

                    this.transactionService.deposit(TransactionCmd.Deposit.getAccountNo(args),
                            TransactionCmd.Deposit.getAmount(args));
                    return;
                case TransactionCmd.Withdraw.CMD:
                    if (!TransactionCmd.Withdraw.isValidArgsStructure(args)) {
                        throw new IllegalArgumentException("Invalid Withdraw cmd arguments!");
                    }

                    this.transactionService.withdraw(TransactionCmd.Withdraw.getAccountNo(args),
                            TransactionCmd.Deposit.getAmount(args));
                    return;
                case TransactionCmd.Balance.CMD:
                    if (!TransactionCmd.Balance.isValidArgsStructure(args)) {
                        throw new IllegalArgumentException("Invalid Withdraw cmd arguments!");
                    }

                    this.transactionService.balance(TransactionCmd.Balance.getAccountNo(args));
                    return;
                default:
                    throw new IllegalArgumentException("Invalid command!");
            }
        } catch (Throwable exception) {
            LOGGER.error("failed to process {}", dto);
            LOGGER.error(exception.getMessage());
        }
    }

}
