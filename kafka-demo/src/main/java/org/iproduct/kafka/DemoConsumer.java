package org.iproduct.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.json.simple.JSONObject;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DemoConsumer {
    private Properties props = new Properties();
    KafkaConsumer<String, String> consumer;
    Map<String, Integer> eventMap = new ConcurrentHashMap<String, Integer>();

    private class DemoProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            System.out.println(">>>" + recordMetadata);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    public DemoConsumer() {
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "EventCounter");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }

    public void run() {
        consumer.subscribe(Collections.singletonList("events"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records)
                {
                    log.debug("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n",
                    record.topic(), record.partition(), record.offset(),
                            record.key(), record.value());
                    int updatedCount = 1;
                    if (eventMap.containsKey(record.value())) {
                        updatedCount = eventMap.get(record.value()) + 1;
                    }
                    eventMap.put(record.value(), updatedCount);
                    JSONObject json = new JSONObject(eventMap);
                    System.out.println(">>>>>>>>>" + json.toString());
                }
            }
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DemoConsumer demoProducer = new DemoConsumer();
        demoProducer.run();
        System.out.println("Press <Enter> to finish");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
