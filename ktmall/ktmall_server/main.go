package main

import (
	"github.com/labstack/echo"
)

func main() {
	e := echo.New()
	e.Debug = true
	e.HideBanner = true

	routerRegister(e)

	e.Logger.Fatal(e.Start(":9999"))
}
