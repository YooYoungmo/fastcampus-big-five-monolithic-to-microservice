package fastcampus.bigfive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fastcampus.bigfive.dtos.EvaluationResult;
import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.services.PersonalityTestService;

@RestController
public class TestRestController {

    @Autowired
    private PersonalityTestService personalityTestService;

    @PostMapping("/api/tests")
    public EvaluationResult test(@RequestBody LinePoints linePoints) {
        var evaluation = personalityTestService.evaluate(linePoints);

        var result = EvaluationResult.builder()
            .extraversionValue(evaluation.getExtraversionValue())
            .neuroticismValue(evaluation.getNeuroticismValue())
            .conscientiousnessValue(evaluation.getConscientiousnessValue())
            .agreeablenessValue(evaluation.getAgreeablenessValue())
            .opennessValue(evaluation.getOpennessValue())
            .commentary(evaluation.getCommentary().getComments())
            .build();

        return result;
    }
}
