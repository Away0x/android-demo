package context

import (
	"ktmall/bootstrap/app"
	"ktmall/bootstrap/config"
	"ktmall/database"
	"net/http"

	"github.com/labstack/echo/v4"

	"github.com/jinzhu/gorm"
)

func (c *AppContext) Test() error {
	return c.JSON(http.StatusOK, map[string]interface{}{
		"test": "xxxx",
	})
}

func (c *AppContext) DB() *gorm.DB {
	return database.DBManager()
}

func (c *AppContext) Config() *config.Config {
	return config.G_Config
}

func (c *AppContext) App() *app.Application {
	return app.G_Application
}

func (c *AppContext) Engine() *echo.Echo {
	return c.App().Engine
}
