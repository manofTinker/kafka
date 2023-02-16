package com.lishuai.kafka_01.Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author lishuai
 * @date 2022/8/19
 */
public class CustomerConsumer3 {
    public static void main(String[] args) {

        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.30.128:9092,192.168.30.128:9093,192.168.30.128:9094");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        ArrayList<String> list = new ArrayList<>();

        list.add("TestTopic2");

        kafkaConsumer.subscribe(list);

        while (true){

            kafkaConsumer.poll(1).forEach(System.out::println);

        }

    }
}
