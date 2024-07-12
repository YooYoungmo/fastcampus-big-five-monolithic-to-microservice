package fastcampus.bigfive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fastcampus.bigfive.dtos.EvaluationResult;
import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.services.PersonalityTestService;

@Controller
public class TestController {

    @Autowired
    private PersonalityTestService personalityTestService;

    @PostMapping("/tests")
    public String test(@ModelAttribute("linePoints") LinePoints linePoints, Model model) {
        var evaluation = personalityTestService.evaluate(linePoints);

        var result = EvaluationResult.builder()
        .extraversionValue(evaluation.getExtraversionValue())
        .neuroticismValue(evaluation.getNeuroticismValue())
        .conscientiousnessValue(evaluation.getConscientiousnessValue())
        .agreeablenessValue(evaluation.getAgreeablenessValue())
        .opennessValue(evaluation.getOpennessValue())
        .commentary(evaluation.getCommentary().getComments())
        .build();

        model.addAttribute("evaluation", result);
        return "result";
    }
}