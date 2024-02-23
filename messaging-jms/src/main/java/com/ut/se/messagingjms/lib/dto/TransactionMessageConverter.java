package com.ut.se.messagingjms.lib.dto;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.Arrays;

public class TransactionMessageConverter implements MessageConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMessageDTO.class);

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        TransactionMessageDTO systemMessageDTO = (TransactionMessageDTO) object;
        String cmd = systemMessageDTO.toString();
        LOGGER.info("System Message: {}", cmd);

        return session.createTextMessage(cmd);
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        String cmd = textMessage.getText();
        LOGGER.info("command: {}", cmd);
        String[] cmdComponents = cmd.split(" ");

        return new TransactionMessageDTO(cmdComponents[0],
                Arrays.stream(cmdComponents, 1, cmdComponents.length).toArray(String[]::new));
    }
}
