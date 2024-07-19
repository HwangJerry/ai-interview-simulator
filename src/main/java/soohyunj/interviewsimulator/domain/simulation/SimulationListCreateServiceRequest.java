package soohyunj.interviewsimulator.domain.simulation;

import java.util.List;

public record SimulationListCreateServiceRequest(
        String simulationListName,
        List<String> memberQAIds
) {
}
