package soohyunj.interviewsimulator.infra;

import java.util.List;

public record AudioReadUrlResponse(
        List<String> audioUrls
) {
    public static AudioReadUrlResponse of(List<String> audioUrls) {
        return new AudioReadUrlResponse(audioUrls);
    }
}
