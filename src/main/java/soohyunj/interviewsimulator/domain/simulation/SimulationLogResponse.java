package soohyunj.interviewsimulator.domain.simulation;

import soohyunj.interviewsimulator.infra.SimulationLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record SimulationLogResponse(
        String id,
        String title,
        List<SimulationLog> simulationLogs,
        LocalDateTime timestamp
) {
}
