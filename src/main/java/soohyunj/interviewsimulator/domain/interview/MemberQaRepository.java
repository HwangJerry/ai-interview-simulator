package soohyunj.interviewsimulator.domain.interview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberQaRepository extends JpaRepository<MemberQA, String> {
    @Query("select m from MemberQA m where m.member.id = :memberId order by m.createdAt desc")
    List<MemberQA> findByMemberId(String memberId);
}
