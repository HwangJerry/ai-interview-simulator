package soohyunj.interviewsimulator.domain.preset;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;

@Entity
@Getter
@Table(name = "preset_qa")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PresetQA extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "preset_qa_id")
    private String id;

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(name = "preset_dir", nullable = true)
    private PresetDir presetDir;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preset_list_id", nullable = true)
    private PresetList presetList;

    public PresetReadServiceResponse toDto() {
        return new PresetReadServiceResponse(this.id, this.question, this.answer);
    }
}
