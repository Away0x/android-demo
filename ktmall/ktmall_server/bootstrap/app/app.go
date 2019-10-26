package app

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

func Setup(app *Application) {
	G_Application = app
}
