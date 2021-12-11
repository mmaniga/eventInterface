package com.c1exchange.meta.EventsInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventsInterfaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventsInterfaceApplication.class, args);
	}
}
