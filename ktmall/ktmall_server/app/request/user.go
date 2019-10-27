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
	Mobile string `json:"mobile"`
	Pwd    string `json:"pwd"`
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
	validate.Base
	Name   string
	Icon   string
	Gender string
	Sign   string
}
