package soohyunj.interviewsimulator.auth.dto;

public record IdToken(
        String idToken
) {
    public static IdToken of(String idToken) {
        return new IdToken(idToken);
    }
}
