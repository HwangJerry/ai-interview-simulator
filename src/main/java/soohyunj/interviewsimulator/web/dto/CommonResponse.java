package soohyunj.interviewsimulator.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import soohyunj.interviewsimulator.exception.errorcode.BaseErrorCode;

import static soohyunj.interviewsimulator.web.StatusCode.*;

public record CommonResponse<T>(
    String code,
    String message,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T result
) {
    public static <T> CommonResponse<T> ok(T data, String message) {
        return new CommonResponse<>(OK.toString(), message, data);
    }

    public static <T> CommonResponse<T> ok(T data) {
        return new CommonResponse<>(OK.toString(), "호출에 성공했습니다.", data);
    }

    public static <T> CommonResponse<T> created(String message) {
        return new CommonResponse<>(CREATED.toString(), message, null);
    }

    public static <T> CommonResponse<T> badRequest(BaseErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResponse<T> unauthorized(BaseErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResponse<T> forbidden(BaseErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResponse<T> notFound(BaseErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResponse<T> conflict(BaseErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
