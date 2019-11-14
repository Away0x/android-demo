package request

import (
	"ktmall/app/models"

	"github.com/Away0x/validate"
)

// 用户注册
type UserRegisterReq struct {
	validate.Base
	Mobile     string `json:"mobile"`
	Pwd        string `json:"pwd"`
	VerifyCode string `json:"verifyCode"`
}

func (r *UserRegisterReq) Validators() validate.Validators {
	return validate.Validators{
		"mobile": {
			validate.RequiredValidator(r.Mobile),
			func() (msg string) {
				u := new(models.UserInfo)
				if err := models.DB().Where("mobile = ?", r.Mobile).First(&u).Error; err == nil {
					return "账号已被注册"
				}
				return ""
			},
		},
		"pwd": {validate.RequiredValidator(r.Pwd)},
	}
}

func (r *UserRegisterReq) Plugins() validate.Plugins {
	return validate.Plugins{
		VerifyCodePlugin(r.VerifyCode),
	}
}

func (r *UserRegisterReq) Messages() validate.Messages {
	return validate.Messages{
		"mobile": {"手机号不能为空"},
		"pwd":    {"密码不能为空"},
	}
}

// 用户登录
type UserLoginReq struct {
	validate.Base
	Mobile string `json:"mobile" example:"223"`
	Pwd    string `json:"pwd" example:"123"`
	PushId string `json:"pushId"`
}

func (r *UserLoginReq) Validators() validate.Validators {
	return validate.Validators{
		"mobile": {validate.RequiredValidator(r.Mobile)},
		"pwd":    {validate.RequiredValidator(r.Pwd)},
	}
}

func (r *UserLoginReq) Messages() validate.Messages {
	return validate.Messages{
		"mobile": {"手机号不能为空"},
		"pwd":    {"密码不能为空"},
	}
}

// 修改用户
type UserEditReq struct {
	Name   string
	Icon   string
	Gender string
	Sign   string
}

// 忘记密码
type UserForgetPwdReq struct {
	validate.Base
	Mobile     string `json:"mobile"`
	VerifyCode string `json:"verifyCode"`
}

func (r *UserForgetPwdReq) Validators() validate.Validators {
	return validate.Validators{
		"mobile": {validate.RequiredValidator(r.Mobile)},
	}
}

func (r *UserForgetPwdReq) Messages() validate.Messages {
	return validate.Messages{
		"mobile": {"手机号不能为空"},
	}
}

func (r *UserForgetPwdReq) Plugins() validate.Plugins {
	return validate.Plugins{
		VerifyCodePlugin(r.VerifyCode),
	}
}

// 重置密码
type UserResetPwdReq struct {
	validate.Base
	Pwd string `json:"pwd"`
}

func (r *UserResetPwdReq) Validators() validate.Validators {
	return validate.Validators{
		"pwd": {validate.RequiredValidator(r.Pwd)},
	}
}

func (r *UserResetPwdReq) Messages() validate.Messages {
	return validate.Messages{
		"pwd": {"密码不能为空"},
	}
}
