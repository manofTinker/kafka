package com.lishuai.kafka_02.Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 消费者组消费数据
 *
 * @author lishuai
 * @date 2022/12/2
 */
public class Consumer01 {
    public static void main(String[] args) {

        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.30.132:9092,192.168.30.132:9093");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        
        //配置消费者组(必须设置)
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");
        
        //创建消费者对象
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        ArrayList<String> topics = new ArrayList<>();

        topics.add("lishuai");

        kafkaConsumer.subscribe(topics);

        while (true){
            ConsumerRecords<String, String> poll = kafkaConsumer.poll(1);

            for (ConsumerRecord<String, String> stringStringConsumerRecord : poll) {
                System.out.println(stringStringConsumerRecord);
            }
        }


    }
}
