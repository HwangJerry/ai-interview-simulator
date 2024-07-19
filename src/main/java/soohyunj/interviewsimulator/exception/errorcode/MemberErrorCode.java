package soohyunj.interviewsimulator.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
    DUPLICATE_EMAIL(400, "MEMBER_400_1", "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(400, "MEMBER_400_2", "이미 존재하는 닉네임입니다."),
    INVALID_PASSWORD(400, "MEMBER_400_3", "비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL(400, "MEMBER_400_4", "이메일 형식이 올바르지 않습니다."),
    INVALID_NICKNAME(400, "MEMBER_400_5", "닉네임 형식이 올바르지 않습니다."),
    INVALID_NAME(400, "MEMBER_400_6", "이름 형식이 올바르지 않습니다."),
    INVALID_LOGIN_INFO(401,"MEMBER_401_1",  "로그인 유저가 존재하지 않습니다."),
    NOT_FOUND_MEMBER(404, "MEMBER_404", "존재하지 않는 회원입니다.");

    private final Integer httpStatus;
    private final String code;
    private final String message;
}
