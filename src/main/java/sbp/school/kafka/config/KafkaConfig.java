package sbp.school.kafka.config;

import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * Конфигурация Кафка
 */
public class KafkaConfig {
    public static KafkaProducer getKafkaProducer() {
        KafkaProducer producer = new KafkaProducer(KafkaProperties.getKafkaProperties());

        return producer;
    }
}