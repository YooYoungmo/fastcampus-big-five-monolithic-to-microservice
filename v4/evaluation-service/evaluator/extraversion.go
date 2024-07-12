package evaluator

import "evaluation-service/dtos"

type Extraversion struct {
  Evaluator
}

func (e *Extraversion) SumLinePoints(linePoints dtos.LinePoints) uint {
  // 1 + 6 (linepoint 합산)
  return linePoints.LinePoint1 + linePoints.LinePoint6
}

func (e *Extraversion) Eval(sumOfLinePoints uint) (uint, string) {
  return e.EvalDefault(sumOfLinePoints)
}
