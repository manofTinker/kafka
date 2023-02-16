package com.lishuai.kafka_01.producter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * @author lishuai
 * @date 2022/8/17
 */
public class CustomProductPartitions {
    public static void main(String[] args) {

        //设置连接属性
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.30.132:9092");

        //序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //往自定义分区发送数据
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.lishuai.kafka_01.producter.MyPartitional");

        //提高吞吐量设置
        //缓冲区
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,"33554432");
        //提交时间
        properties.put(ProducerConfig.LINGER_MS_CONFIG,"1");
        //批次大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);
        //压缩
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");

        //创建生产者
        KafkaProducer<String, String> objkafka = new KafkaProducer<>(properties);

        //编写策略
        for (int i = 0; i < 5; i++) {

            //策略1(If a partition is specified in the record, use it)
            objkafka.send(new ProducerRecord<>("lishuai", 0,"", "策略1->第"+(i+1)+"条数据"), (r, e) -> {

                if (e == null) {

                    System.out.println("主题->" + r.topic() + " 分区->" + r.partition());

                } else {
                    e.printStackTrace();
                }

            });

            //策略2(If no partition is specified but a key is present choose a partition based on a hash of the key)
//            objkafka.send(new ProducerRecord<>("test2","g", "策略1->第"+(i+1)+"条数据"), (r, e) -> {
//
//                if (e == null) {
//
//                    System.out.println("主题->" + r.topic() + " 分区->" + r.partition());
//
//                } else {
//                    e.printStackTrace();
//                }
//
//            });

            //策略3(If no partition or key is present choose a partition in a round-robin fashion)
//            objkafka.send(new ProducerRecord<>("test2","s"+i), (r, e) -> {
//
//                if (e == null) {
//
//                    System.out.println("主题->" + r.topic() + " 分区->" + r.partition());
//
//                } else {
//                    e.printStackTrace();
//                }
//
//            });
//
        }

        //关闭kafak
        objkafka.close();
    }
}
