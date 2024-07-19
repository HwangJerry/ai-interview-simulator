package soohyunj.interviewsimulator.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public record KakaoProperties(
        String contentType,
        String iss,
        String clientId,
        String clientSecret,
        String redirectUrl
) {
}
