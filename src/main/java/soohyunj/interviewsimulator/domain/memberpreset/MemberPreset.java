package soohyunj.interviewsimulator.domain.memberpreset;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;
import soohyunj.interviewsimulator.domain.member.Member;
import soohyunj.interviewsimulator.domain.preset.PresetQA;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "preset_qa_log")
public class MemberPreset extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preset_qa_id")
    private PresetQA presetQA;

    public MemberPreset(Member member, PresetQA presetQA) {
        this.member = member;
        this.presetQA = presetQA;
    }
}
