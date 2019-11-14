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
