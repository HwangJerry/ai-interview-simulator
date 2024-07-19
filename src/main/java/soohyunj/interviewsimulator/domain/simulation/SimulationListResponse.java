package soohyunj.interviewsimulator.domain.simulation;

import java.util.List;
import java.util.Map;

public record SimulationListResponse(
        String simulationListId,
        String simulationListName,
        List<SimulationReadResponse> simulationList
) {
}
