package com.example.kafkademo.controller;


import com.example.kafkademo.producer.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderProducer producer;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        String id = String.valueOf(body.getOrDefault("id", "no-id"));
        String payload = body.toString();
        producer.send(id, payload);
        return ResponseEntity.accepted().body(Map.of("status", "ENQUEUED", "id", id));
    }
}