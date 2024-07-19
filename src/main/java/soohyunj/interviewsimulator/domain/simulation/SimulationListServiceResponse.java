package soohyunj.interviewsimulator.domain.simulation;

import java.util.List;

public record SimulationListServiceResponse(
        String simulationListId,
        String simulationListName,
        List<SimulationReadResponse> simulationList
) {
    public SimulationListResponse toResponse() {
        return new SimulationListResponse(this.simulationListId, this.simulationListName, this.simulationList);
    }
}
