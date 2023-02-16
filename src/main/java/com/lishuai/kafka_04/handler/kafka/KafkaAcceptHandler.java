package com.lishuai.kafka_04.handler.kafka;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 发布
 *
 * @author lishuai
 * @date 2023/2/10
 */
@Component
public class KafkaAcceptHandler {

    @Autowired
    private KafkaTemplate kafkaTemplate;



    public void publish(String topic,String message){
        kafkaTemplate.send(topic,message);
    }

}
