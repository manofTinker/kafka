package com.lishuai.kafka_04.config;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lishuai
 * @date 2023/2/9
 */
@SuppressWarnings("all")
@Configuration
@EnableKafka
public class KafkaConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String innerServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String innerGroupid;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String innerEnableAutoCommit;
    @Value("${spring.kafka.listener.concurrency}")
    private String innerConcurrency;
    @Value("${spring.kafka.producer.acks}")
    private String innerAcks;
    @Value("${spring.kafka.properties.max.poll.interval.ms}")
    private String innePollTimeout;
    @Value("${spring.kafka.consumer.max-poll-records}")
    private String innerPollRecogs;
    @Value("${spring.kafka.listener.ack-mode}")
    private String innerAckModel;


    //定义生产者config
    @Bean
    public Map<String, Object> ProducerConfig() {
        HashMap<String, Object> prohMap = new HashMap<>();
        prohMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, innerServers);
        prohMap.put(ProducerConfig.BATCH_SIZE_CONFIG, 4096);
        prohMap.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        prohMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);
        prohMap.put(ProducerConfig.RETRIES_CONFIG, 0);

        prohMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prohMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return prohMap;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<String, Object>(ProducerConfig());
    }

    /**
     * 使用KafkaTemplate来构建
     *
     * @return
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

    //定义kafka消费者config
    @Bean
    public Map<String, Object> ConsumerConfig() {
        HashMap<String, Object> ComMap = new HashMap<>();
        ComMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, innerServers);
        ComMap.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer1");
        ComMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        ComMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return ComMap;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(ConsumerConfig());
    }

    @Bean
    @Primary
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> KafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(Integer.getInteger(innerConcurrency));
        factory.getContainerProperties().setPollTimeout(Integer.getInteger(innePollTimeout));
        //factory.getContainerProperties().setAckMode();

        return factory;
    }
}
