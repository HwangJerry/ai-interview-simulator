package soohyunj.interviewsimulator.domain.simulation;

import java.util.List;

public record SimulationListCreateRequest(
        String simulationListName,
        List<String> memberQAIds
) {
    public SimulationListCreateServiceRequest toServiceRequest() {
        return new SimulationListCreateServiceRequest(simulationListName, memberQAIds);
    }
}
