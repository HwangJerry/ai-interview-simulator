package soohyunj.interviewsimulator.domain.chatgpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soohyunj.interviewsimulator.client.chatgpt.ChatGptClient;
import soohyunj.interviewsimulator.domain.chatgpt.ChatGptProperties;
import soohyunj.interviewsimulator.domain.chatgpt.ChatGptServiceResponse;
import soohyunj.interviewsimulator.exception.NoAnswerGptException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptService {
    private final ChatGptClient chatGptClient;
    private final ChatGptProperties chatGptProperties;

    public String getResponse(List<Map<String, String>> messages) {
        ResponseEntity<Map<String, Object>> response = chatGptClient.getResponse(
                chatGptProperties.getAuthorization(),
                getRequestBody(messages)
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return ChatGptServiceResponse.of((List<Map<String, Object>>) response.getBody().get("choices")).completions().getFirst().chatGptMessage().content();
        }
        throw new NoAnswerGptException();
    }

    public Map<String, String> getUserMessage(String message) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("role", "user");
        messageMap.put("content", message);
        return messageMap;
    }

    public Map<String, String> getAssistantMessage(String message) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("role", "assistant");
        messageMap.put("content", message);
        return messageMap;
    }

    public Map<String, Object> getRequestBody(List<Map<String, String>> messages) {
        Map<String, Object> requestBody = chatGptProperties.getRequestBody();
        requestBody.put("messages", messages);
        return requestBody;
    }

}
