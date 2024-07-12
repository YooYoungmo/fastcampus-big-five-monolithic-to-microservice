package evaluator

import "evaluation-service/dtos"

type Openness struct {
  Evaluator
}

func (e *Openness) SumLinePoints(linePoints dtos.LinePoints) uint {
  // 3 + 8 + 11
  return linePoints.LinePoint3 + linePoints.LinePoint8 + linePoints.LinePoint11
}

func (e *Openness) Eval(sumOfLinePoints uint) (uint, string) {
  // 8점 이하 낮음, 9~10점 중하, 11~12 중상, 13~15 높음
  switch sum := sumOfLinePoints; {
  case sum <= 8:
    return 1, "낮음"
  case sum >= 9 && sum <= 10:
    return 2, "중하"
  case sum >= 11 && sum <= 12:
    return 3, "중상"
  case sum >= 13 && sum <= 15:
    return 4, "높음"
  }
  return 1, "낮음"
}
