package pl.pwilkosz.productsmanagement.productsdemo.Kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerProperties {

    private static final String KAFKA_ADDRESS = "kafka.ampw.com:9092";

    public static Properties producerProperties(){
        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_ADDRESS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }
}
