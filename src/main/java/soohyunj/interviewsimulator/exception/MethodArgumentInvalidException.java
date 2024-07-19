package soohyunj.interviewsimulator.exception;

import soohyunj.interviewsimulator.exception.errorcode.BaseErrorCode;
import soohyunj.interviewsimulator.exception.errorcode.ValidationErrorCode;

public class MethodArgumentInvalidException extends BaseException{
    public MethodArgumentInvalidException() {
        super(ValidationErrorCode.METHOD_ARGUMENT_INVALID);
    }
}
