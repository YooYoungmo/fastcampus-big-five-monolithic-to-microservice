package middlewares

import (
  "evaluation-service/helpers"
  "github.com/gin-gonic/gin"
  "gorm.io/gorm"
)

func DatabaseConnection(db *gorm.DB) gin.HandlerFunc {
  return func(c *gin.Context) {
    req := c.Request
    ctx := req.Context()
    c.Request = c.Request.WithContext(helpers.ContextHelper().SetDB(ctx, db))
    c.Next()
  }
}
