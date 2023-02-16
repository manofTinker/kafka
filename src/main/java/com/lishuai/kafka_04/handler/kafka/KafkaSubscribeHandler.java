package com.lishuai.kafka_04.handler.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author lishuai
 * @date 2023/2/10
 */
@Component
public class KafkaSubscribeHandler {

    @KafkaListener(topics = "", containerGroup = "consumer1", containerFactory = "KafkaListenerContainerFactory")
    public void Subscribe(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        System.out.println("收到的消息---》" + record.value());
        acknowledgment.acknowledge();
    }

}
