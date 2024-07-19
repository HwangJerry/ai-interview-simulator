package soohyunj.interviewsimulator.domain.simulation;

public record SimulationStartServiceRequest(
        String simulationListName,
        Integer simulationListSize
        ) {
}
