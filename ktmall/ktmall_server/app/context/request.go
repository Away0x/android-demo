package context

import (
	"strconv"

	"github.com/Away0x/validate"
	"github.com/labstack/echo/v4"
)

// 包装错误为 validate.Messages 类型
func wrapRequestError(err error) error {
	switch typed := err.(type) {
	case *echo.HTTPError:
		return validate.Messages{
			"bind": {typed.Message.(string)},
		}
	case validate.Messages:
		return typed
	default:
		return validate.Messages{
			"error": {typed.Error()},
		}
	}
}

func (c *AppContext) BindReq(v interface{}) error {
	if err := c.Context.Bind(v); err != nil {
		return wrapRequestError(err)
	}

	return nil
}

func (c *AppContext) BindAndValidate(v validate.Validater) error {
	if err := c.Context.Bind(v); err != nil {
		return wrapRequestError(err)
	}

	if err, ok := validate.Run(v); !ok {
		return err
	}

	return nil
}

func (c *AppContext) BindAndValidateWithConfig(v validate.Validater, createConfig func(req validate.Validater) validate.Config) error {
	if err := c.Context.Bind(v); err != nil {
		return wrapRequestError(err)
	}

	if err, ok := validate.RunWithConfig(v, createConfig(v)); !ok {
		return err
	}

	return nil
}

func (c *AppContext) IntParam(key string) (int, error) {
	i, err := strconv.Atoi(c.Param(key))
	if err != nil {
		return 0, err
	}

	return i, nil
}

func (c *AppContext) UintParam(key string) (uint, error) {
	i, err := strconv.Atoi(c.Param(key))
	if err != nil {
		return 0, err
	}

	return uint(i), nil
}
