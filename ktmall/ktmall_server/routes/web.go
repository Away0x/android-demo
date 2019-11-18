package routes

import (
	"ktmall/app/context"
	"time"

	"github.com/labstack/echo/v4"
)

const (
	WebPrefix = ""
)

func registerWeb(e *echo.Echo) {
	ee := e.Group(WebPrefix)

	context.RegisterHandler(ee.GET, "", func(c *context.AppContext) error {
		now := time.Now()

		return c.RenderHTML("welcome", context.TplData{
			"time": now.Format("2006-01-02"),
		})
	}).Name = "welcome"

}
