package soohyunj.interviewsimulator.auth.dto;

public record UserInfo(
        String nickname,
        String email,
        String profileImage
) {
    public UserInfo(String email) {
        this(null, email, null);
    }
}
