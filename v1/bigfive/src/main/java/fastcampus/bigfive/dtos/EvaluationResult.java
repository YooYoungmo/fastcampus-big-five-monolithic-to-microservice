package fastcampus.bigfive.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EvaluationResult {
    private int extraversionValue;      
    private int neuroticismValue;      
    private int conscientiousnessValue;
    private int agreeablenessValue;
    private int opennessValue;
    private String commentary;    
}
