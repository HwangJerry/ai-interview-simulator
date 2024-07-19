package soohyunj.interviewsimulator.exception;

import soohyunj.interviewsimulator.exception.errorcode.SecurityErrorCode;

public class NotAuthenticatedException extends BaseException {
        public NotAuthenticatedException() {
            super(SecurityErrorCode.NOT_AUTHENTIATED_ERROR);
        }
}
