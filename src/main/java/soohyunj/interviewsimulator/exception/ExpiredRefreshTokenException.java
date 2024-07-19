package soohyunj.interviewsimulator.exception;

public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException() {
        super("만료된 refresh token입니다.");
    }
}
