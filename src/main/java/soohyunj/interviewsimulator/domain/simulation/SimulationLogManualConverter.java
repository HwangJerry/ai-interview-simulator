package soohyunj.interviewsimulator.domain.simulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SimulationLogManualConverter {

    public static String convertToDatabaseColumn(List<Map<String, String>> attribute) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            log.debug("Converting to database column: {}", attribute);
            log.debug("Converted to database column: {}", objectMapper.writeValueAsString(attribute));
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to database column", e);
        }
    }

    public static List<Map<String, String>> convertToEntityAttribute(String dbData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to entity attribute", e);
        }
    }
}
