package evaluator

import "evaluation-service/dtos"

type Neuroticism struct {
  Evaluator
}

func (e *Neuroticism) SumLinePoints(linePoints dtos.LinePoints) uint {
  // 5 + 10
  return linePoints.LinePoint5 + linePoints.LinePoint10
}

func (e *Neuroticism) Eval(sumOfLinePoints uint) (uint, string) {
  return e.EvalDefault(sumOfLinePoints)
}
