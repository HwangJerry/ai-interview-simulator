package soohyunj.interviewsimulator.domain.simulation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soohyunj.interviewsimulator.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface SimulationListRepository extends JpaRepository<SimulationList, String> {
    @Query("select s from SimulationList s where s.member = :member order by s.createdAt desc")
    List<SimulationList> findByMember(Member member);

    Optional<SimulationList> findByName(String name);

    Boolean existsByName(String name);
}
