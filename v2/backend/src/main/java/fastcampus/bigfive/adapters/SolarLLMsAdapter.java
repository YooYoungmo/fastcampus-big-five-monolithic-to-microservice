package fastcampus.bigfive.adapters;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SolarLLMsAdapter {
    @Value("${solar.api.key}")
    private String apiKey;

    public String chat(String query) {        
        // https://developers.upstage.ai/docs/apis/chat
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", String.format("Bearer %s", apiKey));

        RestTemplate restTemplate = new RestTemplate();
        var requestBody = ChatRequest.builder()
            .model("solar-1-mini-chat")
            .messages(Arrays.asList(new Message("system", query)))
            .stream(false)
            .build();

        HttpEntity<ChatRequest> request = new HttpEntity<>(requestBody, headers);
        ChatResponse response = restTemplate.postForObject("https://api.upstage.ai/v1/solar/chat/completions", request, ChatResponse.class);

        return response.getChoices().get(0).getMessage().getContent();
    }
}
