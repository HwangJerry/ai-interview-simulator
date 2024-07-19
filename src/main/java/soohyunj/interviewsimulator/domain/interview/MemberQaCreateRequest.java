package soohyunj.interviewsimulator.domain.interview;

import soohyunj.interviewsimulator.util.SecurityUtil;

public record MemberQaCreateRequest(
        String question,
        String answer,
        String presetQaId
) {
    public MemberQaCreateServiceRequest toServiceDto() {
        return new MemberQaCreateServiceRequest(
                SecurityUtil.getLoginMemberId(),
                this.question(),
                this.answer(),
                this.presetQaId()
        );
    }
}
