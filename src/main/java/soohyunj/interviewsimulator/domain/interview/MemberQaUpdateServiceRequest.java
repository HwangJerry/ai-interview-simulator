package soohyunj.interviewsimulator.domain.interview;

public record MemberQaUpdateServiceRequest(
        String memberQaId,
        String loginMemberId,
        String question,
        String answer
) {
}
