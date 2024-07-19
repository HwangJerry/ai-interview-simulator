package soohyunj.interviewsimulator.domain.simulation;

import soohyunj.interviewsimulator.exception.BaseException;

public class YetStartedSimulationException extends BaseException {

    public YetStartedSimulationException() {
        super(SimulationErrorCode.YET_STARTED_SIMULATION);
    }
}
