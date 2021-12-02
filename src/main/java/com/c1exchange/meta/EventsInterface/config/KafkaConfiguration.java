package com.c1exchange.meta.EventsInterface.config;

import com.c1exchange.meta.EventsInterface.dto.Event;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        System.out.println("Entering Kafka Admin");
        System.out.println("Bootstrap Address " + bootstrapAddress);
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    public NewTopic newTopic(String topicName, int numberOfPartition, int replicas) {
        return TopicBuilder.name(topicName).partitions(numberOfPartition).replicas(replicas).build();
    }

    @Bean
    public ProducerFactory<String,Event> producerFactory() {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.c1exchange.meta.EventsInterface.dto");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String,Event> consumerFactory() {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "eventInterfaceGroup-1");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.c1exchange.meta.EventsInterface.dto");

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String,Event> kafkaTemplate() {
        return new KafkaTemplate<String,Event>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String,Event> factory =
                new ConcurrentKafkaListenerContainerFactory<String,Event>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // From old Code --

    @Bean
    public NewTopic topicIdentify() {
        return TopicBuilder.name("identify")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicGroup() {
        return TopicBuilder.name("group")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicTrack() {
        return TopicBuilder.name("track")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicPage() {
        return TopicBuilder.name("page")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicScreen() {
        return TopicBuilder.name("screen")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicUnknown() {
        return TopicBuilder.name("unknown")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
