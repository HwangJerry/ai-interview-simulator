package soohyunj.interviewsimulator.domain.simulation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soohyunj.interviewsimulator.exception.errorcode.BaseErrorCode;

import static soohyunj.interviewsimulator.web.StatusCode.*;

@Getter
@AllArgsConstructor
public enum SimulationErrorCode implements BaseErrorCode {
    DUPLICATE_SIMULATION_NAME(CONFLICT, "SIMULATION_409", "이미 존재하는 시뮬레이션 이름입니다."),
    YET_STARTED_SIMULATION(BAD_REQUEST, "SIMULATION_400", "시뮬레이션이 아직 시작되지 않았습니다."),
    NOT_FOUND_SIMULATION_LOG(NOT_FOUND, "SIMULATION_404", "시뮬레이션 로그가 존재하지 않습니다. 시뮬레이션이 정상적으로 시작되었는지 확인해주세요.");

    private final Integer httpStatus;
    private final String code;
    private final String message;
}
