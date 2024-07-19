package soohyunj.interviewsimulator.domain.interview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soohyunj.interviewsimulator.domain.simulation.SimulationQARepository;
import soohyunj.interviewsimulator.exception.AccessDeniedRequestException;
import soohyunj.interviewsimulator.exception.InvalidLoginInfoException;
import soohyunj.interviewsimulator.domain.memberpreset.MemberPreset;
import soohyunj.interviewsimulator.domain.memberpreset.MemberPresetRepository;
import soohyunj.interviewsimulator.domain.member.Member;
import soohyunj.interviewsimulator.domain.member.MemberRepository;
import soohyunj.interviewsimulator.domain.preset.PresetQA;
import soohyunj.interviewsimulator.domain.preset.PresetQaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberQaService {
    private final MemberQaRepository memberQaRepository;
    private final MemberRepository memberRepository;
    private final PresetQaRepository presetQaRepository;
    private final MemberPresetRepository memberPresetRepository;
    private final SimulationQARepository simulationQARepository;

    @Transactional
    public void createCustomMemberQa(MemberQaCreateServiceRequest request) {
        Member loginMember = memberRepository.findById(request.memberId())
                .orElseThrow(InvalidLoginInfoException::new);
        memberQaRepository.save(new MemberQA(request.question(), request.answer(), loginMember));
        if (request.presetQaId() == null) {
            return;
        }
        PresetQA presetQA = presetQaRepository.findById(request.presetQaId())
                .orElseThrow(() -> new IllegalArgumentException("프리셋 면접리스트가 존재하지 않습니다."));
        memberPresetRepository.save(new MemberPreset(loginMember, presetQA));
    }

    @Transactional
    public MemberQaServiceResponse updateMemberQa(MemberQaUpdateServiceRequest request) {
        Member loginMember = memberRepository.findById(request.loginMemberId())
                .orElseThrow(InvalidLoginInfoException::new);
        MemberQA memberQA = memberQaRepository.findById(request.memberQaId())
                .orElseThrow(NotFoundMemberQaException::new);
        if (!memberQA.isOwner(loginMember)) {
            throw new AccessDeniedRequestException();
        }
        memberQA.update(request.question(), request.answer());
        return new MemberQaServiceResponse(memberQA.getQuestion(), memberQA.getAnswer());
    }

    @Transactional
    public MemberQaServiceResponse deleteMemberQa(MemberQaDeleteServiceRequest request) {
        Member loginMember = memberRepository.findById(request.loginMemberId())
                .orElseThrow(InvalidLoginInfoException::new);
        MemberQA memberQA = memberQaRepository.findById(request.memberQaId())
                .orElseThrow(NotFoundMemberQaException::new);
        if (!memberQA.isOwner(loginMember)) {
            throw new AccessDeniedRequestException();
        }
        simulationQARepository.deleteByMemberQA(memberQA);
        memberQaRepository.delete(memberQA);
        return new MemberQaServiceResponse(memberQA.getQuestion(), memberQA.getAnswer());
    }

    public List<MemberQaReadResponse> findAllMemberQa(String loginMemberId) {
        Member loginMember = memberRepository.findById(loginMemberId)
                .orElseThrow(InvalidLoginInfoException::new);
        List<MemberQA> memberQAList = memberQaRepository.findByMemberId(loginMember.getId());
        return MemberQaReadResponse.of(memberQAList);
    }
}
