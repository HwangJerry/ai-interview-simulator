package soohyunj.interviewsimulator.domain.interview;

import java.util.List;

public record MemberQaReadResponse(
        String id,
        String question,
        String answer
) {
    public static List<MemberQaReadResponse> of(List<MemberQA> memberQAList) {
        return memberQAList.stream()
                .map(memberQA -> new MemberQaReadResponse(
                        memberQA.getId(),
                        memberQA.getQuestion(),
                        memberQA.getAnswer()
                ))
                .toList();
    }
}
