package main

import (
	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
)

func routerRegister(e *echo.Echo) {
	e.Use(middleware.Recover())
	e.Use(middleware.Logger())

}
