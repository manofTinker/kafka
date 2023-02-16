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
public class CustomProductTransaction {
    public static void main(String[] args) {

        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.30.128:9093");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"transaction_id_0");

        // 设置事务 id（必须），事务 id 任意起名
        //properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_id_0");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        //初始化事务
        kafkaProducer.initTransactions();
        //开启事务
        kafkaProducer.beginTransaction();
        try {
            //主题
            kafkaProducer.send(new ProducerRecord<>("test2","hhhh"));

            //int i = 1 /0;

            //提交事务
            kafkaProducer.commitTransaction();
        } catch (Exception e) {
            //终止事务,回滚
            kafkaProducer.abortTransaction();
        } finally {
            kafkaProducer.close();
        }
    }
}
