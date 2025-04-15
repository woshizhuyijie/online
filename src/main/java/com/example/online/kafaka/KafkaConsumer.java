package com.example.online.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "course-topic", groupId = "campus-group")
    public void listen(String message) {
        System.out.println("收到Kafka消息：" + message);
    }
}
