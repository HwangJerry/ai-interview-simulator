package soohyunj.interviewsimulator.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static soohyunj.interviewsimulator.web.StatusCode.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements BaseErrorCode {
    NOT_AUTHENTIATED_ERROR(UNAUTHORIZED, "SECURITY_401", "사용자가 인증되지 않았습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "TOKEN_401_1", "토큰이 유효하지않습니다."),
    INVALID_SIGNATURE_TOKEN(UNAUTHORIZED, "TOKEN_401_2", "토큰의 Signature가 일치하지 않습니다."),
    INCORRECT_ISSUER_TOKEN(UNAUTHORIZED, "TOKEN_401_3", "토큰 발급처가 일치하지 않습니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "TOKEN_401_4", "토큰이 만료되었습니다."),
    NOT_MATCHED_TOKEN_TYPE(UNAUTHORIZED, "TOKEN_401_5", "토큰의 타입이 일치하지 않아 디코딩할 수 없습니다.");


    private final Integer httpStatus;
    private final String code;
    private final String message;
}
