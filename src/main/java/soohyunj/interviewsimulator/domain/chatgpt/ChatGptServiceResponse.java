package soohyunj.interviewsimulator.domain.chatgpt;

import java.util.List;
import java.util.Map;

public record ChatGptServiceResponse(
        List<ChatGptData> completions
) {
    public static ChatGptServiceResponse of(List<Map<String, Object>> response) {
        return new ChatGptServiceResponse(
                response.stream()
                        .map(ChatGptData::of)
                        .toList()
        );
    }
}
