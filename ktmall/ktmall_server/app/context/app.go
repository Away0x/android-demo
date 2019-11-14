package context

import (
	"ktmall/config"
	"ktmall/database"

	"github.com/labstack/echo/v4"

	"github.com/jinzhu/gorm"
)

func (c *AppContext) DB() *gorm.DB {
	return database.DBManager()
}

func (c *AppContext) Config() *config.Config {
	return config.G_Config
}

func (c *AppContext) App() *config.Application {
	return config.G_Application
}

func (c *AppContext) Engine() *echo.Echo {
	return c.App().Engine
}
