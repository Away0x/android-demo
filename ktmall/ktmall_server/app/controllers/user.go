package handler

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/request"
	"ktmall/common"

	"github.com/Away0x/validate"
)

/// 用户相关接口

// 用户注册
func UserRegister(c *context.AppContext) (err error) {
	req := new(request.UserRegisterReq)
	if err = c.BindAndValidate(req); err != nil {
		return err
	}

	u := &models.UserInfo{
		Mobile: req.Mobile,
		Pwd:    req.Pwd,
	}
	if err = u.Create(); err != nil {
		return err
	}

	t, err := c.TokenSign(u)
	if err != nil {
		return err
	}

	return c.SuccessResp(map[string]interface{}{
		"user":  u.Serialize(),
		"token": t,
	})
}

// 用户登录
func UserLogin(c *context.AppContext) (err error) {
	req := new(request.UserLoginReq)
	if err := c.BindAndValidate(req); err != nil {
		return err
	}

	u := new(models.UserInfo)
	if err = models.DB().Where("mobile = ?", req.Mobile).First(u).Error; err != nil {
		return c.ErrorResp(common.ResultCodeResourceError, "用户不存在")
	}
	if err = u.Compare(req.Pwd); err != nil {
		return c.ErrorResp(common.ResultCodeError, "密码错误")
	}

	t, err := c.TokenSign(u)
	if err != nil {
		return err
	}

	return c.SuccessResp(map[string]interface{}{
		"user":  u.Serialize(),
		"token": t,
	})
}

// 刷新 token
func UserRefreshToken(c *context.AppContext, s string) error {
	t, err := c.TokenRefresh(s)
	if err != nil {
		return err
	}
	return c.SuccessResp(t)
}

// 用户登出
func UserLogout(c *context.AppContext, u *models.UserInfo, s string) error {
	c.TokenForget(s)
	return c.SuccessResp(nil)
}

// 忘记密码
func UserForgetPwd(c *context.AppContext) (err error) {
	req := &struct {
		validate.Base
		Mobile     string `json:"mobile"`
		VerifyCode string `json:"verifyCode"`
	}{}
	if err = c.BindAndValidateWithConfig(req, func(r validate.Validater) validate.Config {
		return validate.Config{Plugins: validate.Plugins{request.VerifyCodePlugin(req.VerifyCode)}}
	}); err != nil {
		return err
	}

	if req.Mobile == "" {
		return c.ErrorResp(common.ResultCodeReqError, "手机号不能为空")
	}
	u := new(models.UserInfo)
	if err = c.DB().Where("mobile = ?", req.Mobile).First(u).Error; err != nil {
		return c.ErrorResp(common.ResultCodeResourceError, "用户不存在")
	}

	// 处理忘记密码的逻辑

	return c.SuccessResp(u)
}

// 重置密码
func UserResetPwd(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	req := &struct {
		Pwd string `json:"pwd"`
	}{}
	if err = c.BindReq(req); err != nil {
		return err
	}

	u.Pwd = req.Pwd
	if err = u.Update(); err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "密码更新失败")
	}

	t, err := c.TokenSign(u)
	if err != nil {
		return err
	}

	return c.SuccessResp(map[string]interface{}{
		"user":  u.Serialize(),
		"token": t,
	})
}

// 编辑用户资料
func UserEdit(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	req := new(request.UserEditReq)
	if err = c.BindReq(req); err != nil {
		return err
	}

	u.Name = req.Name
	u.Icon = req.Icon
	u.Gender = req.Gender
	u.Sign = req.Sign
	if err = u.Update(); err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "用户更新失败")
	}

	return c.SuccessResp(u)
}

// 获取用户资料
func UserInfo(c *context.AppContext, u *models.UserInfo, s string) error {
	return c.SuccessResp(u)
}
