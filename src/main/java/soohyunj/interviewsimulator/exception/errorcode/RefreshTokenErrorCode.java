package soohyunj.interviewsimulator.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefreshTokenErrorCode implements BaseErrorCode {
    EXPIRED_REFRESH_TOKEN(401, "REFRESH_TOKEN_401", "해당 리프레쉬토큰은 만료되었습니다.");

    private final Integer httpStatus;
    private final String code;
    private final String message;
}
