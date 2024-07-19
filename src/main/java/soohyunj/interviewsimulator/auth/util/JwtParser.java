package soohyunj.interviewsimulator.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soohyunj.interviewsimulator.auth.dto.UserInfo;
import soohyunj.interviewsimulator.exception.ExpiredTokenException;
import soohyunj.interviewsimulator.exception.IncorrectIssuerTokenException;
import soohyunj.interviewsimulator.exception.InvalidSignatureTokenException;
import soohyunj.interviewsimulator.exception.InvalidTokenException;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtParser {
    private final ObjectMapper objectMapper;

    public String getKid(String idToken) {
        try {
            String[] tokenSplit = idToken.split("\\.");
            String header = tokenSplit[0];
            String decodedHeader = new String(Base64.getUrlDecoder().decode(header), StandardCharsets.UTF_8);
            return objectMapper.readValue(decodedHeader, Map.class).get("kid").toString();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public UserInfo getUserInfo(String idToken, RSAPublicKey publicKey, String iss, String aud) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .requireIssuer(iss)
                    .requireAudience(aud)
                    .build()
                    .parseSignedClaims(idToken)
                    .getPayload();
            return new UserInfo(claims.get("nickname", String.class), claims.get("email", String.class), claims.get("profile_image", String.class));
        } catch (SignatureException e) {
            throw new InvalidSignatureTokenException();
        } catch (IncorrectClaimException e) {
            throw new IncorrectIssuerTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
