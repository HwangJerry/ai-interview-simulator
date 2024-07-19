package soohyunj.interviewsimulator.domain.interview;

public record MemberQaCreateServiceRequest(
        String memberId,
        String question,
        String answer,
        String presetQaId
) {
}
