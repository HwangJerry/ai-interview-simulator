package soohyunj.interviewsimulator.domain.interview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soohyunj.interviewsimulator.exception.errorcode.BaseErrorCode;

import static soohyunj.interviewsimulator.web.StatusCode.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum MemberQaErrorCode implements BaseErrorCode {
    NOT_FOUND_MEMBER_QA(NOT_FOUND, "GLOBAL_404", "해당 면접리스트를 찾을 수 없습니다.");

    private final Integer httpStatus;
    private final String code;
    private final String message;

}
