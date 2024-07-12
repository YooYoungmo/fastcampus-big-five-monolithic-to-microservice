package services

import (
  "context"
  "evaluation-service/dtos"
  "evaluation-service/entities"
  "evaluation-service/repositories"
)

type EvaluationService struct {
  evaluationRepository *repositories.EvaluationRepository
}

func (s EvaluationService) Evaluate(ctx context.Context, linePoints dtos.LinePoints) (*entities.EvaluationEntity, error) {
  entity := entities.EvaluationEntity{}
  entity.Evaluate(linePoints)
  
  if err := s.evaluationRepository.Create(ctx, &entity); err != nil {
    return nil, err
  }
  
  return &entity, nil
}

func NewEvaluationService(evaluationRepository *repositories.EvaluationRepository) *EvaluationService {
  return &EvaluationService{
    evaluationRepository: evaluationRepository,
  }
}
