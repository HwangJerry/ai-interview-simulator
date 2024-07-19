package soohyunj.interviewsimulator.domain.preset;

public record PresetReadServiceResponse(
        String id,
        String question,
        String answer
) {
    public PresetReadResponse toResponse() {
        return new PresetReadResponse(this.id(), this.question(), this.answer());
    }
}
