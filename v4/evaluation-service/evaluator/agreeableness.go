package evaluator

import "evaluation-service/dtos"

type Agreeableness struct {
  Evaluator
}

func (e *Agreeableness) SumLinePoints(linePoints dtos.LinePoints) uint {
  // 2 + 7 + 12
  return linePoints.LinePoint2 + linePoints.LinePoint7 + linePoints.LinePoint12
}

func (e *Agreeableness) Eval(sumOfLinePoints uint) (uint, string) {
  // 10점 이하 낮음, 11~12점 중하, 13 중상, 14~15 높음
  switch sum := sumOfLinePoints; {
  case sum <= 10:
    return 1, "낮음"
  case sum >= 11 && sum <= 12:
    return 2, "중하"
  case sum == 13:
    return 3, "중상"
  case sum >= 14 && sum <= 15:
    return 4, "높음"
  }
  return 1, "낮음"
}
