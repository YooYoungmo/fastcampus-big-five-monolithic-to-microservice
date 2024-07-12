package fastcampus.bigfive.adapters;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommentaryServiceAdapter {
    public String query(CommentaryRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        CommentaryResponse response = restTemplate.postForObject("http://localhost:8000/api/commentaries", request, CommentaryResponse.class);
        return response.getComments();
    }
}
