package soohyunj.interviewsimulator.domain.simulation;

public record SimulationContinueRequest(
        String reply
) {
    public SimulationContinueServiceRequest toServiceRequest() {
        return new SimulationContinueServiceRequest(reply);
    }
}
