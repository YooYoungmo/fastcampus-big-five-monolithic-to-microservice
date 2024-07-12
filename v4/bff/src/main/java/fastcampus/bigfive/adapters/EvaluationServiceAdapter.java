package fastcampus.bigfive.adapters;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fastcampus.bigfive.dtos.LinePoints;

@Component
public class EvaluationServiceAdapter {
    public EvaluationResponse evaluate(LinePoints linePoints) {
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.postForObject("http://localhost:8081/api/evaluations", linePoints, EvaluationResponse.class);
        return response;
    }
}
