package fastcampus.bigfive.entities;

import fastcampus.bigfive.dtos.LinePoints;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "evaluations")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    
    /**
     * 외향성
     */
    private String extraversion;
    private int extraversionValue;      

    /**
     * 신경성
     */
    private String neuroticism;
    private int neuroticismValue;      

    /**
     * 성실성
     */
    private String conscientiousness;
    private int conscientiousnessValue;

    /**
     * 친화성
     */
    private int agreeablenessValue;
    private String agreeableness;

    /**
     * 개방성
     */
    private int opennessValue;
    private String openness;

    
    /**
     * 평가 값에 대한 해설
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commentary_id", referencedColumnName = "id")
    private Commentary commentary;

    /**
     * 항목 포인트를 기준으로 5가지 평가값을 구한다
     * @param linePoints
     */
    public void evaluate(LinePoints linePoints) {
        evaluateExtraversion(linePoints);
        evaluateNeuroticism(linePoints);
        evaluateConscientiousness(linePoints);
        evaluateAgreeableness(linePoints);
        evaluateOpenness(linePoints);
    }

    /**
     * 개방성을 평가한다
     * @param linePoints
     */
    private void evaluateOpenness(LinePoints linePoints) {
        int sum = linePoints.getLinePoint3() + linePoints.getLinePoint8() + linePoints.getLinePoint11();

        if(sum <= 8) {
            opennessValue = 1;
            openness = "낮음";
        } else if(sum >= 9 && sum <= 10) {
            opennessValue = 2;
            openness = "중하";
        } else if(sum >= 11 && sum <= 12) {
            opennessValue = 3;
            openness = "중상";
        } else if(sum >= 13 && sum <= 15) {
            opennessValue = 4;
            openness = "높음";
        } else {
            opennessValue = 4;
            openness = "높음";
        }
    }

    /**
     * 친화성을 평가한다
     * @param linePoints
     */
    private void evaluateAgreeableness(LinePoints linePoints) {
        int sum = linePoints.getLinePoint2() + linePoints.getLinePoint7() + linePoints.getLinePoint12();

        if(sum <= 10) {
            agreeablenessValue = 1;
            agreeableness = "낮음";
        } else if(sum >= 11 && sum <= 12) {
            agreeablenessValue = 2;
            agreeableness = "중하";
        } else if(sum == 13) {
            agreeablenessValue = 3;
            agreeableness = "중상";
        } else if(sum >= 14 && sum <= 15) {
            agreeablenessValue = 4;
            agreeableness = "높음";
        } else {
            agreeablenessValue = 4;
            agreeableness = "높음";
        }
    }

    /**
     * 성실성을 평가한다
     * @param linePoints
     */
    private void evaluateConscientiousness(LinePoints linePoints) {
        int sum = linePoints.getLinePoint4() + linePoints.getLinePoint9();
        switch (sum) {
            case 2, 3, 4:
                conscientiousnessValue = 1;
                conscientiousness = "낮음";
                break;
            case 5, 6:
                conscientiousnessValue = 2;
                conscientiousness = "중간";
                break;
            case 7, 8:
                conscientiousnessValue = 3;
                conscientiousness = "중상";
                break;
            case 9, 10:
                conscientiousnessValue = 4;
                conscientiousness = "높음";
                break;
            default:
                conscientiousnessValue = 1;
                conscientiousness = "낮음";
                break;
        }   
    }

    /**
     * 신경성을 평가한다
     * @param linePoints
     */
    private void evaluateNeuroticism(LinePoints linePoints) {
        int sum = linePoints.getLinePoint5() + linePoints.getLinePoint10();
        switch (sum) {
            case 2, 3, 4:
                neuroticismValue = 1;
                neuroticism = "낮음";
                break;
            case 5, 6:
                neuroticismValue = 2;
                neuroticism = "중간";
                break;
            case 7, 8:
                neuroticismValue = 3;
                neuroticism = "중상";
                break;
            case 9, 10:
                neuroticismValue = 4;
                neuroticism = "높음";
                break;
            default:
                neuroticismValue = 1;
                neuroticism = "낮음";
                break;
        }   
    }

    /**
     * 외향성을 평가한다.
     * @param linePoints
     */
    private void evaluateExtraversion(LinePoints linePoints) {
        int sum = linePoints.getLinePoint1() + linePoints.getLinePoint6();
        switch (sum) {
            case 2, 3, 4:
                extraversionValue = 1;
                extraversion = "낮음";
                break;
            case 5, 6:
                extraversionValue = 2;
                extraversion = "중간";
                break;
            case 7, 8:
                extraversionValue = 3;
                extraversion = "중상";
                break;
            case 9, 10:
                extraversionValue = 4;
                extraversion = "높음";
                break;
            default:
                extraversionValue = 1;
                extraversion = "낮음";
                break;
        }
    }

    // Getter, Setter
    public int getExtraversionValue() {
        return extraversionValue;
    }
    
    public int getNeuroticismValue() {
        return neuroticismValue;
    }
    
	public int getConscientiousnessValue() {
        return conscientiousnessValue;
    }
    
    public int getAgreeablenessValue() {
        return agreeablenessValue;
    }
    
    public int getOpennessValue() {
        return opennessValue;
    }

    public Commentary getCommentary() {
        return commentary;
    }

    public void setCommentary(Commentary commentary) {
        this.commentary = commentary;
    }

    public String makeQueryForCommentary() {
        return String.format("""
            최근에 Big Five personality traits 검사 결과를 받았어. 외향성(extraversion)이 %s,
            신경성(neuroticism)이 %s, 성실성(conscientiousness)이 %s,
            친화성(agreeableness)이 %s, 개방성(openness)이 %s 이라는 거야.
            그런데 이 결과를 어떻게 해석해야 할지 모르겠어.
            각 성격 요인별 강점을 살리고, 약점을 보완하는 긍정적인 방향으로 삶을 개선할 수 있는 구체적인 제안을 해줘.
            나에 대해 스스로 잘 알고 싶어 그래서 요약본과 각 요인별 자세하게 해석해주었으면해
            내가 지향하는 방향과 다른 결과 때문에 내가 스스로 잘 하고 있는지 걱정이 드네.
        """, this.extraversion, this.neuroticism, this.conscientiousness, this.agreeableness, this.openness);
    }    
}
