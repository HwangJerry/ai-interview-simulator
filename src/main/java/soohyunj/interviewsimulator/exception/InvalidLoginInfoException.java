package soohyunj.interviewsimulator.exception;

import soohyunj.interviewsimulator.exception.errorcode.MemberErrorCode;

public class InvalidLoginInfoException extends BaseException {
    public InvalidLoginInfoException() {
        super(MemberErrorCode.INVALID_LOGIN_INFO);
    }
}
