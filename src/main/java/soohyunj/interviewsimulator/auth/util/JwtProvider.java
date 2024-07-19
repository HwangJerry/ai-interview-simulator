package soohyunj.interviewsimulator.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import soohyunj.interviewsimulator.auth.dto.DecodedJwtToken;
import soohyunj.interviewsimulator.auth.properties.JwtProperties;
import soohyunj.interviewsimulator.exception.ExpiredTokenException;
import soohyunj.interviewsimulator.exception.InvalidSignatureTokenException;
import soohyunj.interviewsimulator.exception.InvalidTokenException;
import soohyunj.interviewsimulator.exception.NotMatchedTokenTypeException;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

import static soohyunj.interviewsimulator.redis.REDIS_HEADER.ACCESS_TOKEN;
import static soohyunj.interviewsimulator.redis.REDIS_HEADER.REFRESH_TOKEN;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
    }

    public Jws<Claims> getClaim(String token) {
        log.debug("token : {}", token);
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (SignatureException e) {
            throw new InvalidSignatureTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public String generateAccessToken(String userId, String role) {
        log.debug("access token exp : {}", jwtProperties.accessTokenExp());
        return issueToken(userId, role, ACCESS_TOKEN, jwtProperties.accessTokenExp());
    }

    public String generateRefreshToken(String userId, String role) {
        return issueToken(userId, role, REFRESH_TOKEN, jwtProperties.refreshTokenExp());
    }

    public DecodedJwtToken decodeToken(String token, String type) {
        Claims claims = getClaim(token).getPayload();
        checkType(claims, type);
        return new DecodedJwtToken(claims.getSubject(), String.valueOf(claims.get("role")), String.valueOf(claims.get("type")));
    }

    private String issueToken(String userId, String role, String type, Long time) {
        Date now = new Date();
        return Jwts.builder()
                .issuer("ai-interview-simulator")
                .subject(userId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + time))
                .claim("type", type)
                .claim("role", role)
                .signWith(getSecretKey())
                .compact();
    }

    private void checkType(Claims claims, String type) {
        if (type.equals(String.valueOf(claims.get("type")))) {
            return;
        } else {
            throw new NotMatchedTokenTypeException();
        }
    }
}

