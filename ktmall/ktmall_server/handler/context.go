package handler

import (
	"encoding/json"
	"errors"
	"net/http"
	"os"
	"path"
	"strconv"
	"strings"

	"github.com/labstack/echo"
)

type (
	AppContext struct {
		echo.Context
	}
)

const (
	dataPath = "data"
)

func NewAppContext(c echo.Context) *AppContext {
	return &AppContext{Context: c}
}

func ContextMiddleware(h echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		cc := &AppContext{Context: c}
		return h(cc)
	}
}

func ContextRegisterHandler(
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

func (c *AppContext) intParam(key string) (int, error) {
	i, err := strconv.Atoi(c.Param(key))
	if err != nil {
		return 0, errors.New("int param not fount")
	}

	return i, nil
}

func loadData(name string, i interface{}) error {
	file, err := os.Open(path.Join(dataPath, name+".json"))
	if err != nil {
		return err
	}

	if err = json.NewDecoder(file).Decode(i); err != nil {
		return err
	}

	return nil
}

func (c *AppContext) Test() error {
	return c.JSON(http.StatusOK, map[string]interface{}{
		"test": "xxxx",
	})
}
