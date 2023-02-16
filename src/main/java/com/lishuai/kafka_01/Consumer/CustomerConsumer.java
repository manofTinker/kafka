package com.lishuai.kafka_01.Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author lishuai
 * @date 2022/8/18
 */
public class CustomerConsumer {
    public static void main(String[] args) {

        //配置
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.30.128:9092");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test1");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String,String>(properties);

        ArrayList<String> list = new ArrayList<>();
        list.add("TestTopic2");

        kafkaConsumer.subscribe(list);

        while(true){

            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(1);
            consumerRecords.forEach(System.out::println);

        }

    }
}
