package soohyunj.interviewsimulator.exception;

public class NotMatchedTokenTypeException extends RuntimeException {
    public NotMatchedTokenTypeException() {
        super("토큰 타입이 일치하지 않습니다.");
    }
}
