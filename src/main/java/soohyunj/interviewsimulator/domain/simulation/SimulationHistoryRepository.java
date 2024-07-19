package soohyunj.interviewsimulator.domain.simulation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soohyunj.interviewsimulator.domain.member.Member;

import java.util.List;

public interface SimulationHistoryRepository extends JpaRepository<SimulationHistory, String> {
    @Query("select s from SimulationHistory s where s.member = :loginMember order by s.createdAt desc")
    List<SimulationHistory> findByMember(Member loginMember);
}
