package com.c1exchange.meta.EventsInterface.controller;

import com.c1exchange.meta.EventsInterface.dto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventsController {

    @Autowired
    private KafkaTemplate<String,Event> kafkaTemplate;

    @Autowired
    KafkaAdmin kafkaAdmin;

    @GetMapping("/event")
    public String event() {
        Event event = new Event();
        kafkaTemplate.send("page",event);
        return event.toString();
    }

    @PostMapping("/events")
    public String events(@RequestBody Event event) {
        try {
            kafkaTemplate.send("page",event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Json Message posted success";
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
