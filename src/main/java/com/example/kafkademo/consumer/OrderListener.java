package com.example.kafkademo.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class OrderListener {


    @KafkaListener(topics = "${app.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(ConsumerRecord<String, String> record) {
        System.out.printf("[CONSUMER] topic=%s part=%d off=%d key=%s value=%s%n",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());
    }
}