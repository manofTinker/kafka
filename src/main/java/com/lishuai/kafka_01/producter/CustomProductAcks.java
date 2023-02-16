package com.lishuai.kafka_01.producter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author lishuai
 * @date 2022/8/17
 */
public class CustomProductAcks {
    public static void main(String[] args) {

        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.30.128:9093");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //配置应答acks
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");

        //配置重试次数默认为int的范围
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);


        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0 ; i < 20 ; i++){

            kafkaProducer.send(new ProducerRecord<>("TestTopic2", 0,"","hhh"), (r, e) -> {

                if (e == null) {
                    System.out.println("主题->" + r.topic() + " 分区->" + r.partition());
                } else {
                    e.printStackTrace();
                }

            });

        }

        kafkaProducer.close();


    }
}
