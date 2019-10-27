package context

import (
	"ktmall/common"
	"ktmall/common/serializer"
	"net/http"
)

func (c *AppContext) SuccessResp(data interface{}) error {
	data = serializer.Serialize(data) // 如果有序列化方法，则调用
	return c.JSON(http.StatusOK, common.NewCommonResponse(common.ResultCodeSuccess, "", data))
}

func (c *AppContext) ErrorResp(status common.ResultCode, message string) error {
	return c.JSON(http.StatusOK, common.NewCommonResponse(status, message, nil))
}
