package sbp.school.kafka.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbp.school.kafka.config.KafkaProperties;
import sbp.school.kafka.entity.Transaction;

/**
 * Сервис для отправки сообщений брокеру.
 * Каждый тип операции передается в свою партицию.
 */
public class ProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

    private final KafkaProducer<String, Transaction> producer;
    private final String topic;

    public ProducerService() {
        this.producer = new KafkaProducer(KafkaProperties.getKafkaProperties());
        this.topic = KafkaProperties.getTopicProperty();
    }

    /**
     * Вызывает отправку в кафку трех транзакции разных типов
     */
    public void send(Transaction transaction) {
        try {
            producer.send(new ProducerRecord<>(topic, transaction.getOperationType(), transaction),
                    ((metadata, exception) -> {
                        if (exception == null) {
                            LOGGER.info("Success.");
                        }
                        logMetadata(metadata);
                    }));
        } catch (Exception e) {
            LOGGER.info("Ошибка при отправке сообщения в кафку: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void logMetadata(RecordMetadata metadata) {
        LOGGER.info("offset : {}", metadata.offset());
        LOGGER.info("topic : {}", metadata.topic());
        LOGGER.info("partition : {}", metadata.partition());
    }
}
