package soohyunj.interviewsimulator.auth.constant;

import java.util.List;

public final class CORS_PATTERNS {
    public static final List<String> ALLOWED_ORIGINS = List.of(
            "*"
    );
    public static final List<String> ALLOWED_METHODS = List.of(
//            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
            "*"
    );

    public static final List<String> ALLOWED_HEADERS = List.of("*");
    public static final List<String> EXPOSED_HEADERS = List.of("Authorization", "Content-Type");
    public static final Boolean ALLOWED_CREDENTIALS = true;

    // PREFLIGHT 요청을 지속시키는 시간
    public static final Long MAX_AGE_1H = 3600L;

    private CORS_PATTERNS() {}
}
