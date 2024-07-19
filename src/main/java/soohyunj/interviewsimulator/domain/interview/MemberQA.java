package soohyunj.interviewsimulator.domain.interview;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;
import soohyunj.interviewsimulator.domain.member.Member;
import soohyunj.interviewsimulator.domain.simulation.SimulationQA;
import soohyunj.interviewsimulator.domain.simulation.SimulationReadResponse;
import soohyunj.interviewsimulator.domain.simulation.SimulationReadServiceResponse;

import java.util.List;

@Entity
@Getter
@Table(name = "member_qa")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberQA extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_qa_id")
    private String id;

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public MemberQA(String question, String answer, Member member) {
        this.question = question;
        this.answer = answer;
        this.member = member;
    }

    public Boolean isOwner(Member member) {
        return this.member.equals(member);
    }

    public void update(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public SimulationReadServiceResponse toReadServiceResponse() {
        return new SimulationReadServiceResponse(this.question, this.answer);
    }

    public SimulationReadResponse toReadResponse() {
        return new SimulationReadResponse(this.question, this.answer);
    }

    @Override
    public String toString() {
        return "MemberQA{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", member=" + member +
                '}';
    }
}
