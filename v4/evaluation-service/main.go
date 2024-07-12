package main

import (
	"evaluation-service/app"
	"evaluation-service/controllers"
	"evaluation-service/repositories"
	"evaluation-service/services"
)

func main() {
	// create server
	ginApp, err := app.NewGinApp()
	if err != nil {
		panic(err)
	}

	// rest mapping
	evaluationController := controllers.NewEvaluationController(services.NewEvaluationService(&repositories.EvaluationRepository{}))
	ginApp.POST("/api/evaluations", evaluationController.Evaluate)

	// server start
	ginApp.Run("0.0.0.0:8081")
}
