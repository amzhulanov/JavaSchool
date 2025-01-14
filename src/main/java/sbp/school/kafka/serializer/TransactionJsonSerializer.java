package sbp.school.kafka.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.networknt.schema.*;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sbp.school.kafka.entity.Transaction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сериализатор сущности

 */
public class TransactionJsonSerializer implements Serializer<Transaction> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionJsonSerializer.class);

    private static final String JSON_SCHEMA = "src/main/java/sbp/school/kafka/schema/transaction.json";
    /**
     * Конвертирует сущность в строку по json-схеме
     *
     * @param topic  Топик, ассоциированный с сущностью
     * @param entity Сущность с данными
     * @return данные в виде байт
     */
    @Override
    public byte[] serialize(String topic, Transaction entity) {
        if (entity != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            try {
                String value = objectMapper.writeValueAsString(entity);

                Set<ValidationMessage> resultValidation = validationJson(objectMapper.readTree(value),
                        objectMapper.readTree(new File(JSON_SCHEMA)));

                if (!resultValidation.isEmpty()) {
                    String errorMsg = resultValidation.stream()
                            .map(ValidationMessage::getMessage).collect(Collectors.joining(", "));
                    LOGGER.error("Ошибки при валидации схемы данных: {}", errorMsg);
                    throw new JsonSchemaException(errorMsg);
                }

                return value.getBytes(StandardCharsets.UTF_8);
            } catch (JsonProcessingException e) {
                System.out.println("Ошибочка : " + e.getMessage());
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private Set<ValidationMessage> validationJson(JsonNode jsonNode, JsonNode schema) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

        JsonSchema jsonSchema = factory.getSchema(schema);

        return jsonSchema.validate(jsonNode);
    }
}
