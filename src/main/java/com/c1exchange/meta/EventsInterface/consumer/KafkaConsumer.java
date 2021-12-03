package com.c1exchange.meta.EventsInterface.consumer;

import com.c1exchange.meta.EventsInterface.dto.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.segment.analytics.Analytics;
import com.segment.analytics.messages.*;

@Component
public class KafkaConsumer {

    // Note: How is this group anc container factor determined...
    @KafkaListener(groupId = "eventInterfaceGroup-1",topics = "page", containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(Event message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(message);
        System.out.println("Kafka Consumer / Listener Message Processing");
        System.out.println(jsonString);
        // System.out.println("Creating Segment Message Track");
        // TrackMessage message2 = TrackMessage.builder("event").userId("theUserId").build();
        // System.out.println("Track Message constructed");
        // String jsonString2 = mapper.writeValueAsString(message2);
        // System.out.println(message2);
    }
}
