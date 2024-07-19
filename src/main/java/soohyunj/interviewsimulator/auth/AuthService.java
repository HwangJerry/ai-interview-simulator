package soohyunj.interviewsimulator.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soohyunj.interviewsimulator.auth.dto.*;
import soohyunj.interviewsimulator.client.kakaotoken.KakaoTokenClient;
import soohyunj.interviewsimulator.auth.properties.KakaoProperties;
import soohyunj.interviewsimulator.auth.util.JwtParser;
import soohyunj.interviewsimulator.auth.util.JwtProvider;
import soohyunj.interviewsimulator.auth.util.PublicKeyGenerator;
import soohyunj.interviewsimulator.exception.*;
import soohyunj.interviewsimulator.redis.RefreshTokenRedisRepository;
import soohyunj.interviewsimulator.domain.member.Member;
import soohyunj.interviewsimulator.domain.member.MemberRepository;
import soohyunj.interviewsimulator.domain.refreshtoken.RefreshToken;
import soohyunj.interviewsimulator.web.dto.LoginRequest;

import static soohyunj.interviewsimulator.redis.REDIS_HEADER.REFRESH_TOKEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRepository;
    private final KakaoProperties kakaoProperties;
    private final KakaoTokenClient kakaoTokenClient;
    private final JwtParser jwtParser;
    private final JwtProvider jwtProvider;

    public IdToken getIdToken(String loginType, String code) {
        switch (loginType) {
            case "kakao":
                KakaoTokenInfo kakaoTokenInfo = kakaoTokenClient.getToken(
                        kakaoProperties.contentType(),
                        kakaoProperties.clientId(),
                        kakaoProperties.redirectUrl(),
                        code,
                        kakaoProperties.clientSecret());
                return IdToken.of(kakaoTokenInfo.idToken());

            // other login types ...
        }
        throw new NotSupportedLoginTypeException();
    }

    public LoginToken login(String loginType, IdToken request) {
        String idToken = request.idToken();
        String kid = jwtParser.getKid(idToken);
        PublicKeys publicKeys;
        String iss, aud;
        log.debug("kid: {}", kid);
        switch (loginType) {
            case "kakao":
                publicKeys = kakaoTokenClient.getPublicKeys();
                iss = kakaoProperties.iss();
                aud = kakaoProperties.clientId();
                log.debug("iss: {}, aud: {}", iss, aud);
                break;

            // other login types ...
            default:
                throw new NotSupportedLoginTypeException();
        }
        PublicKey key = publicKeys.getKeys().stream()
                .filter(k -> k.getKid().equals(kid))
                .findFirst()
                .orElseThrow(IncorrectIssuerTokenException::new);
        log.debug("key: {}", key);
        UserInfo userInfo = jwtParser.getUserInfo(idToken, PublicKeyGenerator.execute(key), iss, aud);
        log.debug("userInfo.email(): {}", userInfo.email());
        Member member = memberRepository.findByEmail(userInfo.email())
                .orElseGet(() -> memberRepository.save(Member.createUser(userInfo.nickname(), userInfo.email(), userInfo.profileImage())));
        log.debug("member: {}", member);
        String accessToken = jwtProvider.generateAccessToken(member.getId(), member.getRole().getValue());
        String refreshToken = jwtProvider.generateRefreshToken(member.getId(), member.getRole().getValue());
        log.debug("accessToken : {}, refreshToken : {}", accessToken, refreshToken);

        refreshTokenRepository.save(member.getId(), new RefreshToken(member.getId(), refreshToken));
        return new LoginToken(accessToken, refreshToken, member.getId());
    }

    public LoginToken refresh(TokenRefreshRequest request) {
        DecodedJwtToken decodedJwtToken = decodeRefreshToken(request.refreshToken());
        Member member = memberRepository.findById(decodedJwtToken.memberId())
                .orElseThrow(InvalidTokenException::new);

        RefreshToken saved = refreshTokenRepository.findById(member.getId())
                .orElseThrow(InvalidTokenException::new);
        log.debug("saved token: {}", saved.getToken());
        if (saved.getToken().equals(request.refreshToken())) {
            String accessToken = jwtProvider.generateAccessToken(member.getId(), member.getRole().getValue());
            String newRefreshToken = jwtProvider.generateRefreshToken(member.getId(), member.getRole().getValue());
            refreshTokenRepository.save(member.getId(), new RefreshToken(member.getId(), newRefreshToken));
            return new LoginToken(accessToken, newRefreshToken, member.getId());
        } else {
            throw new InvalidTokenException();
        }
    }

    private DecodedJwtToken decodeRefreshToken(String refreshToken) {
        try {
            return jwtProvider.decodeToken(refreshToken, REFRESH_TOKEN);
        } catch (ExpiredTokenException e) {
            throw new ExpiredRefreshTokenException();
        }
    }

    public LoginToken loginV2(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseGet(() -> memberRepository.save(Member.createUser(request.nickname(), request.email(), null)));
        log.debug("member: {}", member);
        String accessToken = jwtProvider.generateAccessToken(member.getId(), member.getRole().getValue());
        String refreshToken = jwtProvider.generateRefreshToken(member.getId(), member.getRole().getValue());
        log.debug("accessToken : {}, refreshToken : {}", accessToken, refreshToken);

        refreshTokenRepository.save(member.getId(), new RefreshToken(member.getId(), refreshToken));
        return new LoginToken(accessToken, refreshToken, member.getId());

    }
}
