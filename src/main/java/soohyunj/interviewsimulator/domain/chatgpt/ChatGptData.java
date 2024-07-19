package soohyunj.interviewsimulator.domain.chatgpt;

import java.util.Map;

public record ChatGptData(
        int index,
        ChatGptMessage chatGptMessage,
        Object logprobs,
        String finishReason
) {
    public static ChatGptData of(Map<String, Object> data) {
        return new ChatGptData(
                (int) data.get("index"),
                ChatGptMessage.of((Map<String, String>) data.get("message")),
                data.get("logprobs"),
                (String) data.get("finish_reason"));
    }
}
