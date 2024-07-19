package soohyunj.interviewsimulator.domain.simulation;

public record SimulationReadServiceResponse(
        String question,
        String answer
) {
    public SimulationReadResponse toResponse() {
        return new SimulationReadResponse(question, answer);
    }
}
