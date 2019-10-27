package request

import (
	"github.com/Away0x/validate"
)

// 验证码
func VerifyCodePlugin(vc string) validate.PluginFunc {
	return func() (string, []validate.ValidatorFunc, []string) {
		return "verifyCode", []validate.ValidatorFunc{
				validate.RequiredValidator(vc),
				func() (msg string) {
					if vc != "123456" {
						return "验证码错误"
					}
					return ""
				},
			}, []string{
				"验证码不能为空",
			}
	}
}
