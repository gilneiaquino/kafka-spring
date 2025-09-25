package com.example.kafkademo;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.time.Duration;
import java.util.List;
import java.util.Properties;


import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
public class KafkaIntegrationTest {


@Container
static KafkaContainer kafka = new KafkaContainer("5.5.3"); // usa Kafka compatível do TC


@Test
void should_send_and_consume() {
String topic = "test-topic";


Properties p = new Properties();
p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


try (KafkaProducer<String, String> producer = new KafkaProducer<>(p)) {
producer.send(new ProducerRecord<>(topic, "k1", "v1"));
producer.flush();
}


Properties c = new Properties();
c.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
c.put(ConsumerConfig.GROUP_ID_CONFIG, "g1");
c.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
c.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
c.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(c)) {
consumer.subscribe(List.of(topic));
var records = consumer.poll(Duration.ofSeconds(5));
assertThat(records.count()).isGreaterThan(0);
}
}
}