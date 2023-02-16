package com.lishuai.kafka_02.Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author lishuai
 * @date 2022/12/2
 */
public class ConsumerPartition {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.30.132:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");

        KafkaConsumer<String, String> objectObjectKafkaConsumer = new KafkaConsumer<>(properties);

        ArrayList<TopicPartition> topic = new ArrayList<>();

        topic.add(new TopicPartition("lishuai",0));
        objectObjectKafkaConsumer.assign(topic);

        while(true){

            ConsumerRecords<String, String> poll = objectObjectKafkaConsumer.poll(1);

            for (ConsumerRecord<String, String> record : poll) {
                System.out.println(record);
            }

        }




    }
}
