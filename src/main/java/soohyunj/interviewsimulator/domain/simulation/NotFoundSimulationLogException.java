package soohyunj.interviewsimulator.domain.simulation;

import soohyunj.interviewsimulator.exception.BaseException;

public class NotFoundSimulationLogException extends BaseException {
    public NotFoundSimulationLogException() {
        super(SimulationErrorCode.NOT_FOUND_SIMULATION_LOG);
    }
}
