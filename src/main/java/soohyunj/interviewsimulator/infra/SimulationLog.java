package soohyunj.interviewsimulator.infra;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soohyunj.interviewsimulator.domain.simulation.SimulationServiceRequest;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimulationLog {

    private Integer order;
    private String question;
    private String answer;
    private String reply;
    private String feedback;

    public SimulationLog(SimulationServiceRequest request, String feedback) {
        this(request.order(), request.question(), request.answer(), request.reply(), feedback);
    }
}
