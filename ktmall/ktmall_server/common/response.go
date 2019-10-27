package common

type (
	ResultCode int

	CommonResponse struct {
		Status  ResultCode  `json:"status"`
		Message string      `json:"message"`
		Data    interface{} `json:"data"`
	}
)

const (
	ResultCodeSuccess ResultCode = 0

	// 其他错误
	ResultCodeError ResultCode = -1
	// 参数错误
	ResultCodeReqError ResultCode = 100
	// 资源错误
	ResultCodeResourceError ResultCode = 101
	// 数据库错误
	ResultCodeDatabaseError ResultCode = 102
)

func NewCommonResponse(status ResultCode, message string, data interface{}) *CommonResponse {
	return &CommonResponse{
		Status:  status,
		Message: message,
		Data:    data,
	}
}
