package soohyunj.interviewsimulator.domain.interview;

import soohyunj.interviewsimulator.util.SecurityUtil;

public record MemberQaDeleteServiceRequest(
        String memberQaId,
        String loginMemberId
) {
    public static MemberQaDeleteServiceRequest of(String memberQaId) {
        return new MemberQaDeleteServiceRequest(
                memberQaId,
                SecurityUtil.getLoginMemberId()
        );
    }
}
