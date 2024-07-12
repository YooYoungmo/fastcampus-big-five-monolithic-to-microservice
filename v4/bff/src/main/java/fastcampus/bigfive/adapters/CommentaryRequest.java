package fastcampus.bigfive.adapters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentaryRequest {
    private Long evaluationId;      
    private String extraversion;      
    private String neuroticism;      
    private String conscientiousness;
    private String agreeableness;
    private String openness;
}
