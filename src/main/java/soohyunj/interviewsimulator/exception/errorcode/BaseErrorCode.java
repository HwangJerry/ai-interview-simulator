package soohyunj.interviewsimulator.exception.errorcode;

public interface BaseErrorCode {
        String getCode();
        String getMessage();
        Integer getHttpStatus();
}
