package controllers

import (
  "evaluation-service/dtos"
  "evaluation-service/services"
  "github.com/gin-gonic/gin"
  "net/http"
)

type EvaluationController struct {
  service *services.EvaluationService
}

func (c EvaluationController) Evaluate(ctx *gin.Context) {
  var linePoints dtos.LinePoints
  
  if err := ctx.BindJSON(&linePoints); err != nil {
    ctx.JSON(http.StatusBadRequest, err.Error())
    return
  }
  
  evaluationEntity, err := c.service.Evaluate(ctx.Request.Context(), linePoints)
  if err != nil {
    ctx.JSON(http.StatusInternalServerError, err.Error())
    return
  }
  
  result := dtos.EvaluationResult{
    Id:                     evaluationEntity.ID,
    ExtraversionValue:      evaluationEntity.ExtraversionValue,
    Extraversion:           evaluationEntity.Extraversion,
    NeuroticismValue:       evaluationEntity.NeuroticismValue,
    Neuroticism:            evaluationEntity.Neuroticism,
    ConscientiousnessValue: evaluationEntity.ConscientiousnessValue,
    Conscientiousness:      evaluationEntity.Conscientiousness,
    AgreeablenessValue:     evaluationEntity.AgreeablenessValue,
    Agreeableness:          evaluationEntity.Agreeableness,
    OpennessValue:          evaluationEntity.OpennessValue,
    Openness:               evaluationEntity.Openness,
  }
  
  ctx.JSON(http.StatusOK, result)
}

func NewEvaluationController(service *services.EvaluationService) *EvaluationController {
  return &EvaluationController{
    service: service,
  }
}
