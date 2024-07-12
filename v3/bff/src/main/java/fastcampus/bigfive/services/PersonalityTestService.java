package fastcampus.bigfive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.entities.Evaluation;
import fastcampus.bigfive.repositories.EvaluationRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonalityTestService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public Evaluation evaluate(LinePoints linePoints) {
        // 평가 하기
        Evaluation evaluation = new Evaluation();
        evaluation.evaluate(linePoints);
        
        // DB에 저장
        evaluationRepository.save(evaluation);
        return evaluation;
    }    
}
