package soohyunj.interviewsimulator.domain.simulation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;
import soohyunj.interviewsimulator.domain.interview.MemberQA;

@Entity
@Getter
@Table(name = "simulation_qa")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimulationQA extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "simulation_qa_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_qa_id", nullable = false)
    private MemberQA memberQA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_list_id", nullable = false)
    private SimulationList simulationList;

    public SimulationQA(MemberQA memberQA, SimulationList simulationList) {
        this.memberQA = memberQA;
        this.simulationList = simulationList;
    }

    public SimulationReadServiceResponse toReadServiceResponse() {
        return this.memberQA.toReadServiceResponse();
    }

    public SimulationReadResponse toReadResponse() {
        return this.memberQA.toReadResponse();
    }

    @Override
    public String toString() {
        return "SimulationQA{" +
                "id='" + id + '\'' +
                ", memberQA=" + memberQA +
                ", simulationList=" + simulationList +
                '}';
    }
}
