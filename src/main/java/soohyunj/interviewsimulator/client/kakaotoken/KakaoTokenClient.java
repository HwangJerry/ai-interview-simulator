package soohyunj.interviewsimulator.client.kakaotoken;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import soohyunj.interviewsimulator.auth.dto.KakaoTokenInfo;
import soohyunj.interviewsimulator.auth.dto.PublicKeys;
import soohyunj.interviewsimulator.auth.util.KakaoTokenErrorDecoder;

@FeignClient(
        name = "kakao-token-client",
        url = "https://kauth.kakao.com",
        configuration = KakaoTokenErrorDecoder.class
)
public interface KakaoTokenClient {
    @PostMapping("/oauth/token?grant_type=authorization_code")
    KakaoTokenInfo getToken(
            @RequestHeader("Content-Type") String contentType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("client_secret") String clientSecret
    );

    @GetMapping("/.well-known/jwks.json")
    PublicKeys getPublicKeys();

}
