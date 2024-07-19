package soohyunj.interviewsimulator.auth.dto;

public record LoginToken(
        String accessToken,
        String refreshToken,
        String loginMemberId
) {
}
