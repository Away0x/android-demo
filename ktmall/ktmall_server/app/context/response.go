package context

import (
	"ktmall/app/response"
	"ktmall/common/serializer"
	"net/http"
)

func (c *AppContext) SuccessResp(data interface{}) error {
	data = serializer.Serialize(data) // 如果有序列化方法，则调用
	return c.JSON(http.StatusOK, response.NewCommonResponse(response.ResultCodeSuccess, "", data))
}

func (c *AppContext) ErrorResp(status response.ResultCode, message string) error {
	return c.JSON(http.StatusOK, response.NewCommonResponse(status, message, nil))
}
