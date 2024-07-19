package soohyunj.interviewsimulator.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public record SimulationLogCache(
        // TODO 이 양식에 맞춰서 presigned url이랑 interviewlog 캐시를 사용하는 서비스까지 전체적으로 리팩토링해보세요.
        String simulationListName,
        String interviewLogId,
        List<SimulationLog> simulationLogs
) {
    public String serialized() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize SimulationLogCache");
        }
    }
}
