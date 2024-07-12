package repositories

import (
  "context"
  "errors"
  "evaluation-service/entities"
  "evaluation-service/helpers"
)

type EvaluationRepository struct {
}

func (EvaluationRepository) Create(ctx context.Context, entity *entities.EvaluationEntity) error {
  db := helpers.ContextHelper().GetDB(ctx)
  
  if err := db.Create(entity).Error; err != nil {
    return errors.New("db error")
  }
  return nil
}
