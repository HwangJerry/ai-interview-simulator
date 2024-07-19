package soohyunj.interviewsimulator.auth.dto;

public record DecodedJwtToken(
        String memberId,
        String role,
        String type
) {
}
