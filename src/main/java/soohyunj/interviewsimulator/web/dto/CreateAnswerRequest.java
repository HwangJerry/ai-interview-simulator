package soohyunj.interviewsimulator.web.dto;

public record CreateAnswerRequest(
        String question
) {
    public CreateAnswerServiceRequest toServiceDto() {
        return new CreateAnswerServiceRequest(
                question
        );
    }
}
