package com.example.kafkademo.producer;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;


    @Value("${app.topic}")
    private String topic;


    public void send(String key, String payload) {
        kafkaTemplate.send(topic, key, payload);
    }
}