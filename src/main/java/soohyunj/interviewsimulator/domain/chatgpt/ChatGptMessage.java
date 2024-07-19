package soohyunj.interviewsimulator.domain.chatgpt;

import java.util.Map;

public record ChatGptMessage(
        String role,
        String content
) {
    public static ChatGptMessage of(Map<String, String> data) {
        return new ChatGptMessage(data.get("role"), data.get("content"));
    }
}
