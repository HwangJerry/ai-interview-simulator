package soohyunj.interviewsimulator.domain.simulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import soohyunj.interviewsimulator.infra.SimulationLog;

import java.util.List;
import java.util.Map;

@Converter
@Slf4j
public class SimulationLogAutoConverter implements AttributeConverter<List<SimulationLog>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<SimulationLog> attribute) {
        try {
            log.debug("Converting to database column: {}", attribute);
            log.debug("Converted to database column: {}", objectMapper.writeValueAsString(attribute));
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to database column", e);
        }
    }

    @Override
    public List<SimulationLog> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to entity attribute", e);
        }
    }
}
