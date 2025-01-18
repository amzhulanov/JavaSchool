package sbp.school.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Параметры запуска Кафка
 */
public class KafkaProperties {
    public static String topicName;
    public static final String PROPS = "application.properties";

    public static Properties getKafkaProperties() {
        Properties propsFromFile = readKafkaProperties();

        Properties kafkaProperties = new Properties();

        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, propsFromFile.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        kafkaProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, propsFromFile.getProperty(ProducerConfig.MAX_BLOCK_MS_CONFIG));
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, propsFromFile.getProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, propsFromFile.getProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
        kafkaProperties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, propsFromFile.getProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG));
        kafkaProperties.put(ProducerConfig.ACKS_CONFIG, propsFromFile.getProperty(ProducerConfig.ACKS_CONFIG));
        kafkaProperties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, propsFromFile.getProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG));

        return kafkaProperties;
    }

    public static String getTopicProperty() {
        return readKafkaProperties().getProperty("kafka.topic");
    }

    public static String getTopicName() {
        return topicName;
    }

    private static Properties readKafkaProperties() {
        InputStream inputStream = KafkaProperties.class.getClassLoader().getResourceAsStream(PROPS);
        Properties properties = new Properties();

        try {
            properties.load(inputStream);
            inputStream.close();
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
