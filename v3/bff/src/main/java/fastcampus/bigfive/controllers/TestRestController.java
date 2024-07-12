package fastcampus.bigfive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fastcampus.bigfive.adapters.CommentaryRequest;
import fastcampus.bigfive.adapters.CommentaryServiceAdapter;
import fastcampus.bigfive.dtos.EvaluationResult;
import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.services.PersonalityTestService;

@RestController
public class TestRestController {

    @Autowired
    private PersonalityTestService personalityTestService;

    @Autowired
    private CommentaryServiceAdapter commentaryServiceAdapter;

    @PostMapping("/api/tests")
    public EvaluationResult test(@RequestBody LinePoints linePoints) {
        // 성격 평가
        var evaluation = personalityTestService.evaluate(linePoints);

        // Commentary Service로 해설 요청
        var commentaryRequest = CommentaryRequest.builder()
            .evaluationId(evaluation.getId())
            .agreeableness(evaluation.getAgreeableness())
            .conscientiousness(evaluation.getConscientiousness())
            .extraversion(evaluation.getExtraversion())
            .neuroticism(evaluation.getNeuroticism())
            .openness(evaluation.getOpenness())
            .build();
        String comments = commentaryServiceAdapter.query(commentaryRequest);
      
        // API 응답 값 생성
        var result = EvaluationResult.builder()
            .extraversionValue(evaluation.getExtraversionValue())
            .neuroticismValue(evaluation.getNeuroticismValue())
            .conscientiousnessValue(evaluation.getConscientiousnessValue())
            .agreeablenessValue(evaluation.getAgreeablenessValue())
            .opennessValue(evaluation.getOpennessValue())
            .commentary(comments)
            .build();

        return result;
    }
}
