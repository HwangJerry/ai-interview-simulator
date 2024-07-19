package soohyunj.interviewsimulator.domain.simulation;

import soohyunj.interviewsimulator.exception.BaseException;
import soohyunj.interviewsimulator.exception.errorcode.MemberErrorCode;

public class NotFoundMemberException extends BaseException {
    public NotFoundMemberException() {
        super(MemberErrorCode.NOT_FOUND_MEMBER);
    }
}
