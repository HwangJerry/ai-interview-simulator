package soohyunj.interviewsimulator.exception;


import soohyunj.interviewsimulator.exception.errorcode.SecurityErrorCode;

public class InvalidTokenException extends BaseException {

        public InvalidTokenException() {
            super(SecurityErrorCode.INVALID_TOKEN);
        }
}
