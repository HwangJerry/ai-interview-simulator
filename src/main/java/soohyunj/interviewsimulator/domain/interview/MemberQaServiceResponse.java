package soohyunj.interviewsimulator.domain.interview;

public record MemberQaServiceResponse(
        String question,
        String answer
) {
    public MemberQaCommandResponse toResponseDto() {
        return new MemberQaCommandResponse(
                this.question(),
                this.answer()
        );
    }
}
