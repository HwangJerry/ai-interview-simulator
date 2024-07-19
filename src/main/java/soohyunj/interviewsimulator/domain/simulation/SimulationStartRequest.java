package soohyunj.interviewsimulator.domain.simulation;

import jakarta.validation.constraints.NotBlank;

public record SimulationStartRequest(
        @NotBlank(message = "시뮬레이션 리스트 이름은 필수입니다.")
        String simulationListName,
        Integer simulationListSize
) {
    public SimulationStartServiceRequest toServiceRequest() {
        return new SimulationStartServiceRequest(simulationListName, simulationListSize);
    }
}
