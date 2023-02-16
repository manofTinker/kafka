package com.lishuai.kafka_01.producter;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author lishuai
 * @date 2022/8/17
 */
public class MyPartitional implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        String s = value.toString();

        int partition;

        if(s.contains("lishuai")){

            partition = 0;

        }else {

            partition = 1;

        }

        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
