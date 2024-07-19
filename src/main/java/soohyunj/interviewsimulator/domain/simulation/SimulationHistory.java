package soohyunj.interviewsimulator.domain.simulation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;
import soohyunj.interviewsimulator.domain.member.Member;
import soohyunj.interviewsimulator.infra.SimulationLogCache;
import soohyunj.interviewsimulator.infra.SimulationLog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Table(name = "simulation_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimulationHistory extends BaseTimeEntity {
    @Id
    @Column(name = "simulation_history_id")
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Convert(converter = SimulationLogAutoConverter.class)
    @Column(name = "simulation_logs", nullable = false, columnDefinition = "TEXT")
    private List<SimulationLog> simulationLogs;

    public SimulationHistory(SimulationLogCache simulationLogCache, Member member) {
        List<SimulationLog> totalLogs = simulationLogCache.simulationLogs();
        List<SimulationLog> extractLogs = totalLogs.stream()
                .filter(simulationLog -> simulationLog.getFeedback() != null)
                .toList();

        this.id = simulationLogCache.interviewLogId();
        this.title = simulationLogCache.simulationListName();
        this.member = member;
        this.simulationLogs = extractLogs;
    }

    public SimulationLogResponse toResponse() {
        simulationLogs.sort(Comparator.comparingInt(SimulationLog::getOrder)); // ordered
        return new SimulationLogResponse(id, title, simulationLogs, getCreatedAt());
    }

    @Override
    public String toString() {
        return "SimulationHistory{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                ", simulationLogs=" + simulationLogs.toString() +
                '}';
    }
}
