package org.iproduct.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class DemoProducer {
    private Properties kafkaProps = new Properties();
    private Producer producer;

    private class DemoProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            System.out.println(">>>" + recordMetadata);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    public DemoProducer() {
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    public void run() {
        for(int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("events", "Precision Products",
                            "test-async" + i);
            try {
                producer.send(record, new DemoProducerCallback());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DemoProducer demoProducer = new DemoProducer();
        demoProducer.run();
        Thread.sleep(5000);
    }
}
