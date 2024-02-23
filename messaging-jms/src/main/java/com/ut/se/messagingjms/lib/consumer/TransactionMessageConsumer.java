package com.ut.se.messagingjms.lib.consumer;

import com.ut.se.messagingjms.config.JmsConfig;
import com.ut.se.messagingjms.lib.dto.TransactionMessageDTO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

@Component
public class TransactionMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMessageConsumer.class);

    @JmsListener(destination = JmsConfig.INPUT_QUEUE_NAME, containerFactory = "UTMessagingJmsListenerContainerFactory")
    public void receive(TransactionMessageDTO dto) {
        LOGGER.info("received cmd: {}", dto);
    }

}
