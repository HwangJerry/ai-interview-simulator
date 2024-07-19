package soohyunj.interviewsimulator.domain.preset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PresetDir {
    NETWORK("네트워크"),
    OS("운영체제"),
    DB("데이터베이스"),
    ALGORITHM("알고리즘"),
    DATA_STRUCTURE("자료구조");

    private final String value;
}
