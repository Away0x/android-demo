package common

import (
	"ktmall/common/errno"
)

type (
	CommonResponse struct {
		Status  int         `json:"status"`
		Message string      `json:"message"`
		Data    interface{} `json:"data"`
	}
)

func NewCommonResponse(code int, message string, data interface{}) *CommonResponse {
	return &CommonResponse{
		Status:  code,
		Message: message,
		Data:    data,
	}
}

func NewSuccessResponse(message string, data interface{}) *CommonResponse {
	return NewCommonResponse(0, message, data)
}

func NewErrResponse(e *errno.Errno) *CommonResponse {
	return NewCommonResponse(e.Code, e.Message, nil)
}
