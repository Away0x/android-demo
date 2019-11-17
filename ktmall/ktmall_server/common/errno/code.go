package errno

import (
	"net/http"
)

var (
	// 其他错误
	UnknownErr = &Errno{HTTPCode: http.StatusOK, Code: -1, Message: "unknown error"}
	// 参数错误
	ReqErr = &Errno{HTTPCode: http.StatusOK, Code: 100, Message: "参数错误"}
	// 资源错误
	ResourceErr = &Errno{HTTPCode: http.StatusOK, Code: 101, Message: "resource error"}
	// 数据库错误
	DatabaseErr = &Errno{HTTPCode: http.StatusOK, Code: 102, Message: "database error"}
	// token 错误
	TokenErr = &Errno{HTTPCode: http.StatusOK, Code: 103, Message: "token error"}
	// route not found
	NotFoundErr = &Errno{HTTPCode: http.StatusOK, Code: 104, Message: "route not found"}
)
