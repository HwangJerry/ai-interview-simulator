package soohyunj.interviewsimulator.domain.interview;

import soohyunj.interviewsimulator.util.SecurityUtil;

public record MemberQaUpdateRequest(
        String question,
        String answer
) {
    public MemberQaUpdateServiceRequest toServiceDto(String memberQaId) {
        return new MemberQaUpdateServiceRequest(
                memberQaId,
                SecurityUtil.getLoginMemberId(),
                this.question(),
                this.answer()
        );
    }
}
