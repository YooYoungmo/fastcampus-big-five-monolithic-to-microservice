package evaluator

import "evaluation-service/dtos"

type Conscientiousness struct {
  Evaluator
}

func (e *Conscientiousness) SumLinePoints(linePoints dtos.LinePoints) uint {
  // 4 + 9
  return linePoints.LinePoint4 + linePoints.LinePoint9
}

func (e *Conscientiousness) Eval(sumOfLinePoints uint) (uint, string) {
  return e.EvalDefault(sumOfLinePoints)
}
