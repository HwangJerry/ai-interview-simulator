package soohyunj.interviewsimulator.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soohyunj.interviewsimulator.exception.errorcode.BaseErrorCode;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
