package com.c1exchange.meta.EventsInterface.controller;

import com.c1exchange.meta.EventsInterface.dto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import com.segment.analytics.Analytics;
import com.segment.analytics.messages.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class EventsController {

    @Autowired
    private KafkaTemplate<String,Event> kafkaTemplate;

    @Autowired
    KafkaAdmin kafkaAdmin;


    @PostMapping(path= "/events", consumes="application/json" )
    public void events(@RequestHeader(name="access-key", required = true) String accessKey,
                         HttpServletResponse response,
                         @RequestBody Map<String, Object> event) {
        try {
            if(accessKey.isEmpty() || accessKey.isBlank()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            Event transferredMessage = new Event();
            String eventType = event.get("type").toString();
            transferredMessage.setType(eventType);
            transferredMessage.setMessage("Empty"); // Not sure why message is used in Event..
            transferredMessage.setSource(event);
            kafkaTemplate.send(eventType,transferredMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }


    @GetMapping("/createTopic")
    public String createTopic(){
        kafkaAdmin.createOrModifyTopics(TopicBuilder.name("Sample-1")
                .partitions(3)
                .replicas(1)
                .build());
        return "Sample-1 Topic created";
    }
}
