package app

import (
	"evaluation-service/app/middlewares"
	"evaluation-service/entities"
	"github.com/gin-gonic/gin"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

func NewGinApp() (*gin.Engine, error) {
	// setup database
	gormDB, err := gorm.Open(sqlite.Open("evaluation.db"), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})

	if err != nil {
		return nil, err
	}

	// migrate schema
	if err := gormDB.AutoMigrate(&entities.EvaluationEntity{}); err != nil {
		return nil, err
	}

	// app server setup
	r := gin.Default()
	r.Use(middlewares.DatabaseConnection(gormDB))

	// return app server
	return r, nil
}
