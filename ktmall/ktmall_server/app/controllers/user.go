package handler

import (
	"ktmall/app/context"
)

/// 用户相关接口

type (
	UserRegisterReq struct {
		Mobile     string `json:"mobile"`
		Pwd        string `json:"pwd"`
		VerifyCode string `json:"verifyCode"`
	}

	UserLoginReq struct {
		Mobile string `json:"mobile"`
		Pwd    string `json:"pwd"`
		PushId string `json:"pushId"`
	}
)

// 用户注册
func UserRegister(c *context.AppContext) error {
	// req := new(UserRegisterReq)
	return nil
}

// 用户登录
func UserLogin(c *context.AppContext) error {
	return nil
}

// 忘记密码
func UserForgetPwd(c *context.AppContext) error {
	return nil
}

// 重置密码
func UserResetPwd(c *context.AppContext) error {
	return nil
}

// 编辑用户资料
func UserEdit(c *context.AppContext) error {
	return nil
}

// 获取用户资料
func UserInfo(c *context.AppContext) error {
	return c.Test()
}
