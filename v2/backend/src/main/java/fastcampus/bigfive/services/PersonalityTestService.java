package fastcampus.bigfive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fastcampus.bigfive.adapters.SolarLLMsAdapter;
import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.entities.Commentary;
import fastcampus.bigfive.entities.Evaluation;
import fastcampus.bigfive.repositories.EvaluationRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonalityTestService {

    @Autowired
    private SolarLLMsAdapter solarLLMsAdapter;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public Evaluation evaluate(LinePoints linePoints) {
        // 평가 하기
        Evaluation evaluation = new Evaluation();
        evaluation.evaluate(linePoints);

        // LLMs에서 해설 가져오기
        String query = evaluation.makeQueryForCommentary();
        String comments = solarLLMsAdapter.chat(query);
        evaluation.setCommentary(new Commentary(comments));

        // 평가 값과 해설 DB에 저장
        evaluationRepository.save(evaluation);
        
        return evaluation;
    }    
}
