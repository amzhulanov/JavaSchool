package sbp.school.kafka;

import org.junit.jupiter.api.Test;
import sbp.school.kafka.entity.Transaction;
import sbp.school.kafka.enums.OperationType;
import sbp.school.kafka.service.ProducerService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Тест сервиса отправки сообщений
 */
class AppTest {

    @Test
    void testSend() {
        ProducerService producerService = new ProducerService();
        producerService.send(new Transaction(UUID.randomUUID(), BigDecimal.ONE, LocalDateTime.now(), OperationType.ACTIVE.name()));
        producerService.send(new Transaction(UUID.randomUUID(), BigDecimal.TEN, LocalDateTime.now(), OperationType.PASSIVE.name()));
        producerService.send(new Transaction(UUID.randomUUID(), BigDecimal.ZERO, LocalDateTime.now(), OperationType.COMMISSION.name()));
    }
}