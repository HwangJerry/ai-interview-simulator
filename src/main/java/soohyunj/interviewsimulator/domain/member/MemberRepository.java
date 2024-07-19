package soohyunj.interviewsimulator.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
}
