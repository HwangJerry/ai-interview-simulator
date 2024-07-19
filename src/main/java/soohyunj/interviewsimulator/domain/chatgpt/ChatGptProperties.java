package soohyunj.interviewsimulator.domain.chatgpt;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "chatgpt")
public record ChatGptProperties(
        String apiKey,
        String model,
        Double temperature
) {
    public String getAuthorization() {
        return "Bearer " + apiKey;
    }

    public Map<String, Object> getRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("temperature", temperature);
        return requestBody;
    }
}
