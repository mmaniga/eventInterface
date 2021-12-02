package com.c1exchange.meta.EventsInterface.consumer;

import com.c1exchange.meta.EventsInterface.dto.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(groupId = "eventInterfaceGroup-1",topics = "page", containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(Event message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(message);
        System.out.println("Receiving Message");
        System.out.println(jsonString);
    }
}
