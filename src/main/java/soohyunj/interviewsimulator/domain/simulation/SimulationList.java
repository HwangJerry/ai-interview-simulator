package soohyunj.interviewsimulator.domain.simulation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;
import soohyunj.interviewsimulator.domain.member.Member;

import java.util.List;

@Entity
@Getter
@Table(name = "simulation_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimulationList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "simulation_list_id")
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public SimulationList(String name, Member member) {
        this.name = name;
        this.member = member;
    }
}
