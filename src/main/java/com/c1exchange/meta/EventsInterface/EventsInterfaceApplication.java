package com.c1exchange.meta.EventsInterface;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
//@SpringBootApplication(scanBasePackages={"com.c1exchange.meta.EventInterface"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@EnableJpaRepositories("com.c1exchange.meta.EventsInterface.repository")
//@ComponentScan(basePackages = {"com.c1exchange.meta.EventsInterface"})
public class EventsInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsInterfaceApplication.class, args);
	}

}
