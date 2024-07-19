package soohyunj.interviewsimulator.domain.interview;

import soohyunj.interviewsimulator.exception.BaseException;

public class NotFoundMemberQaException extends BaseException {
    public NotFoundMemberQaException() {
        super(MemberQaErrorCode.NOT_FOUND_MEMBER_QA);
    }
}
