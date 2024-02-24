package com.ut.se.messagingjms;

import com.ut.se.messagingjms.config.JmsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		jmsTemplate.receive();
 	}

}
