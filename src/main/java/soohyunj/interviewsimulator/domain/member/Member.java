package soohyunj.interviewsimulator.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;
import soohyunj.interviewsimulator.domain.interview.MemberQA;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id")
    private String id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role;

    private Member(String nickname, String email, String profileImage, MemberRole role) {
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
    }

    public static Member createUser(String nickname, String email, String profileImage) {
        return new Member(nickname, email, profileImage, MemberRole.USER);
    }
}
