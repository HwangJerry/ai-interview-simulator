package soohyunj.interviewsimulator.client.chatgpt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name="chat-gpt-client", url="https://api.openai.com/v1")
public interface ChatGptClient {
    @PostMapping("/chat/completions")
    ResponseEntity<Map<String, Object>> getResponse(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, Object> body
    );
}
