package com.ut.se.messagingjms.config;

import com.ut.se.messagingjms.lib.dto.TransactionMessageConverter;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
@EnableJms
public class JmsConfig {

    public static final String INPUT_QUEUE_NAME = "INQ";

    public static final String OUTPUT_QUEUE_NAME = "OUTQ";

    @Bean
    public Queue inputQueue() {
        return new ActiveMQQueue(INPUT_QUEUE_NAME);
    }

    @Bean
    public Queue outputQueue() {
        return new ActiveMQQueue(OUTPUT_QUEUE_NAME);
    }

    @Bean
    public JmsListenerContainerFactory<?> UTMessagingJmsListenerContainerFactory(ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new TransactionMessageConverter();
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Queue outputQueue) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(outputQueue);
        return  jmsTemplate;
    }
}
