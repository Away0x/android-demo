package context

import (
	"strconv"

	"github.com/pkg/errors"

	"github.com/Away0x/validate"
)

// 包装 validate.Messages 类型的 message
func wrapRequestError(err error) error {
	switch typed := err.(type) {
	case validate.Messages:
		msg := "参数错误"
		for _, v := range typed {
			msg = v[0] // 显示第一个错误信息
		}

		return errors.New(msg)
	default:
		return typed
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
