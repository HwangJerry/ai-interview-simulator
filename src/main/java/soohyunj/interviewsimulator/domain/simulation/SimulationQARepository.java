package soohyunj.interviewsimulator.domain.simulation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import soohyunj.interviewsimulator.domain.interview.MemberQA;
import soohyunj.interviewsimulator.domain.member.Member;

import java.util.List;

public interface SimulationQARepository extends JpaRepository<SimulationQA, String> {
    @Query("select s from SimulationQA s where s.simulationList = :simulationList order by s.createdAt asc")
    List<SimulationQA> findBySimulationList(SimulationList simulationList);

//    @Modifying
//    @Query("delete from SimulationQA s where s.simulationList = :simulationList")
    void deleteBySimulationList(SimulationList simulationList);

//    @Modifying
//    @Query("delete from SimulationQA s where s.memberQA = :memberQA")
    void deleteByMemberQA(MemberQA memberQA);
}
