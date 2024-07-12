package entities

import (
  "evaluation-service/dtos"
  "evaluation-service/evaluator"
  "gorm.io/gorm"
)

type EvaluationEntity struct {
  gorm.Model
  ExtraversionValue      uint
  Extraversion           string
  NeuroticismValue       uint
  Neuroticism            string
  ConscientiousnessValue uint
  Conscientiousness      string
  AgreeablenessValue     uint
  Agreeableness          string
  OpennessValue          uint
  Openness               string
}

func (*EvaluationEntity) TableName() string {
  return "evaluations"
}

func (e *EvaluationEntity) Evaluate(linePoints dtos.LinePoints) {
  extraversionEvaluator := evaluator.Evaluator{
    IEvaluator: &evaluator.Extraversion{},
  }
  e.ExtraversionValue, e.Extraversion = extraversionEvaluator.Evaluate(linePoints)
  
  neuroticismEvaluator := evaluator.Evaluator{
    IEvaluator: &evaluator.Neuroticism{},
  }
  e.NeuroticismValue, e.Neuroticism = neuroticismEvaluator.Evaluate(linePoints)
  
  conscientiousnessEvaluator := evaluator.Evaluator{
    IEvaluator: &evaluator.Conscientiousness{},
  }
  e.ConscientiousnessValue, e.Conscientiousness = conscientiousnessEvaluator.Evaluate(linePoints)
  
  agreeablenessEvaluator := evaluator.Evaluator{
    IEvaluator: &evaluator.Agreeableness{},
  }
  e.AgreeablenessValue, e.Agreeableness = agreeablenessEvaluator.Evaluate(linePoints)
  
  opennessEvaluator := evaluator.Evaluator{
    IEvaluator: &evaluator.Openness{},
  }
  e.OpennessValue, e.Openness = opennessEvaluator.Evaluate(linePoints)
}
