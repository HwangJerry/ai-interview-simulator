package soohyunj.interviewsimulator.domain.simulation;

import soohyunj.interviewsimulator.exception.BaseException;

public class DuplicateSimulationNameException extends BaseException {
    public DuplicateSimulationNameException() {
        super(SimulationErrorCode.DUPLICATE_SIMULATION_NAME);
    }
}
