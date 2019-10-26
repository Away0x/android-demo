package context

import (
	"strings"

	"github.com/labstack/echo/v4"
)

type (
	AppContext struct {
		echo.Context
	}
)

func NewAppContext(c echo.Context) *AppContext {
	return &AppContext{Context: c}
}

func Middleware(h echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		cc := &AppContext{Context: c}
		return h(cc)
	}
}

func RegisterHandler(
	fn func(string, echo.HandlerFunc, ...echo.MiddlewareFunc) *echo.Route,
	path string,
	h func(c *AppContext) error,
	m ...echo.MiddlewareFunc,
) *echo.Route {
	if path != "" && !strings.HasPrefix(path, "/") {
		path = "/" + path
	}

	return fn(path, func(c echo.Context) error {
		cc, ok := c.(*AppContext)
		if !ok {
			cc = NewAppContext(c)
			return h(cc)
		}
		return h(cc)
	}, m...)
}
