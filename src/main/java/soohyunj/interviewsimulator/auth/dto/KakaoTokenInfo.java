package soohyunj.interviewsimulator.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokenInfo(
        String accessToken,
        String refreshToken,
        String idToken
) {
}
