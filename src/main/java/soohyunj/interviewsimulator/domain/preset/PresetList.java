package soohyunj.interviewsimulator.domain.preset;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soohyunj.interviewsimulator.domain.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PresetList extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="preset_list_id")
    private String id;

    @Column(name="title")
    private String title;
}
