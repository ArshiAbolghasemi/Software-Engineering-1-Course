package com.ut.se.messagingjms.lib.publisher;

import com.ut.se.messagingjms.lib.dto.SystemResponseDTO;
import jakarta.jms.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SystemResponsePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemResponsePublisher.class);

    private final JmsTemplate jmsTemplate;

    private final Queue outputQueue;

    @Autowired
    public SystemResponsePublisher(JmsTemplate jmsTemplate, Queue outputQueue) {
        this.jmsTemplate = jmsTemplate;
        this.outputQueue = outputQueue;
    }

    public void send(SystemResponseDTO systemResponseDTO) {
        LOGGER.info("send msg {}", systemResponseDTO);
        jmsTemplate.send(this.outputQueue, session -> session.createTextMessage(systemResponseDTO.toString()));
    }
}
