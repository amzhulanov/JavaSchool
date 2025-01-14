package sbp.school.kafka.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Транзакция
 *
 */
public class Transaction {
    private UUID id;
    private BigDecimal summ;
    private LocalDateTime date;
    private String operationType;

    public Transaction(UUID id, BigDecimal summ, LocalDateTime date, String operationType) {
        this.id = id;
        this.summ = summ;
        this.date = date;
        this.operationType = operationType;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getSumm() {
        return summ;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSumm(BigDecimal summ) {
        this.summ = summ;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", summ=" + summ +
                ", date=" + date +
                ", operationType='" + operationType + '\'' +
                '}';
    }
}
