package context

import (
	"ktmall/common/errno"
)

// 一些常见错误的工具函数

// 其他错误
func (c *AppContext) ErrUnknown(err error) error {
	return errno.UnknownErr.WithErr(err)
}

// 参数错误
func (c *AppContext) ErrReq(err error) error {
	return errno.ReqErr.WithErr(err)
}

func (c *AppContext) ErrMsgReq(err error, msg string) error {
	return errno.ReqErr.WithErrMessage(err, msg)
}

// 数据库错误
func (c *AppContext) ErrDatabase(err error) error {
	return errno.DatabaseErr.WithErr(err)
}

func (c *AppContext) ErrMsgDatabase(err error, msg string) error {
	return errno.DatabaseErr.WithErrMessage(err, msg)
}

// 资源错误
func (c *AppContext) ErrResource(err error) error {
	return errno.ResourceErr.WithErr(err)
}

func (c *AppContext) ErrMsgResource(err error, msg string) error {
	return errno.ResourceErr.WithErrMessage(err, msg)
}
