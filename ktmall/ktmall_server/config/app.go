package config

import (
	"github.com/labstack/echo/v4"
)

type (
	Application struct {
		Engine *echo.Echo
	}
)

var (
	G_Application *Application
)

func SetupApp(app *Application) {
	G_Application = app
}

// 根据 route name 获取 route path
func (a *Application) RoutePath(name string, params ...interface{}) string {
	return a.Engine.Reverse(name, params...)
}
