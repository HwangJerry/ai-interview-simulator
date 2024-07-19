package soohyunj.interviewsimulator.domain.simulation;

public record SimulationExecuteRequest(
        Integer order,
        String question,
        String answer,
        String reply

) {
    public SimulationServiceRequest toServiceRequest() {
        return new SimulationServiceRequest(order, question, answer, reply);
    }
}
