package com.lishuai.kafka_01.producter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author lishuai
 * @date 2022/8/16
 */
public class CustomProduct {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //设置连接属性
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.30.128:9093,192.168.30.128:9092,192.168.30.128:9094");

        //设置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());//key序列化
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());//value序列化
        //创建生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < 30; i++) {

            //-----------异步发送-----------------
            //无回调
            //kafkaProducer.send(new ProducerRecord<>("test1", "第" + (i + 1) + "条数据"));

            //有回调
            kafkaProducer.send(new ProducerRecord<>("TestTopic2", "第"+i+"条数据"), (r, e) -> {
                if (e == null) {
                    //没有出现异常，将信息输出到控制台
                    System.out.println("主题:" + r.topic() + "->分区:" + r.partition());
                } else {
                    //有异常
                    e.printStackTrace();
                }
            });

            //-----------同步发送-----------------
            //只要在异步发送后面添加get()方法即可变为同步发送
            //无回调
            //kafkaProducer.send(new ProducerRecord<>("test1", "第" + (i + 1) + "条数据")).get();

            //有回调
//            kafkaProducer.send(new ProducerRecord<>("TestTopic2", "hahah"), (r, e) -> {
//                if (e == null) {
//                    //没有出现异常，将信息输出到控制台
//                    System.out.println("主题:" + r.topic() + "->分区:" + r.partition());
//                } else {
//                    //有异常
//                    e.printStackTrace();
//                }
//            }).get();
        }

        //关闭kafka
        kafkaProducer.close();
    }
}
