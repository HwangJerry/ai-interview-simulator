package soohyunj.interviewsimulator.exception;

import soohyunj.interviewsimulator.exception.errorcode.SecurityErrorCode;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException() {
        super(SecurityErrorCode.EXPIRED_TOKEN);
    }
}
