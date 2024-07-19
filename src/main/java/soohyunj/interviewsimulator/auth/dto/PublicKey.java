package soohyunj.interviewsimulator.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublicKey {
    private String kty; // key type
    private String use; // public key use
    private String alg; // algorithm
    private String kid; // key id
    private String e; // exponent
    private String n; // modulus
}
