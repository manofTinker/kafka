package com.lishuai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Component;

/**
 * @author lishuai
 * @date 2022/12/2
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    private String Servers;
    private String GroupId;
    private String EnableAutoCommit;
    private String Concurrency;


}
