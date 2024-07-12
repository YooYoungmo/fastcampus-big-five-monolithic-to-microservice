package fastcampus.bigfive.adapters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationResponse {
    private int id;
    
    private int extraversionValue;
    private String extraversion;

    private int neuroticismValue;
    private String neuroticism;

    private int conscientiousnessValue;
    private String conscientiousness;

    private int agreeablenessValue;
    private String agreeableness;
    
    private int opennessValue;
    private String openness;
}
