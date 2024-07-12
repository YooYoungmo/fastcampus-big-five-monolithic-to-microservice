package evaluator

import "evaluation-service/dtos"

type IEvaluator interface {
  SumLinePoints(linePoints dtos.LinePoints) uint
  Eval(sumOfLinePoints uint) (uint, string)
}

type Evaluator struct {
  IEvaluator IEvaluator
}

func (e *Evaluator) Evaluate(linePoints dtos.LinePoints) (uint, string) {
  sumOfLinePoints := e.IEvaluator.SumLinePoints(linePoints)
  return e.IEvaluator.Eval(sumOfLinePoints)
}

func (e *Evaluator) EvalDefault(sumOfLinePoints uint) (uint, string) {
  // 2~4 낮음, 5~6 중간, 7~8 중상, 9~10 높음
  switch sumOfLinePoints {
  case 2, 3, 4:
    return 1, "낮음"
  case 5, 6:
    return 2, "중간"
  case 7, 8:
    return 3, "중상"
  case 9, 10:
    return 4, "높음"
  }
  return 1, "낮음"
}
