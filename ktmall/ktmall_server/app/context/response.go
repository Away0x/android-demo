package context

import (
	"ktmall/common"
	"ktmall/common/errno"
	"net/http"
)

func (c *AppContext) SuccessResp(data interface{}) error {
	return c.JSON(http.StatusOK, common.NewSuccessResponse("", data))
}

func (c *AppContext) ErrorResp(e *errno.Errno) error {
	return c.JSON(e.HTTPCode, common.NewErrResponse(e))
}
