package controllers

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/request"
	"ktmall/app/response"
)

/// 用户相关接口

// 用户注册
// @Summary 用户注册
// @Tags user
// @Accept  json
// @Produce  json
// @Param json body request.UserRegisterReq true "注册用户信息"
// @Success 200 {object} response.UserTokenResp
// @Router /user/register [post]
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

	return c.SuccessResp(response.BuildUserAndTokenResp(u, t))
}

// UserLogin 用户登录
// @Summary 用户登录
// @Tags user
// @Accept  json
// @Produce  json
// @Param json body request.UserLoginReq true "登录信息"
// @Success 200 {object} response.UserTokenResp
// @Router /user/login [post]
func UserLogin(c *context.AppContext) (err error) {
	req := new(request.UserLoginReq)
	if err := c.BindAndValidate(req); err != nil {
		return err
	}

	u := new(models.UserInfo)
	if err = models.DB().Where("mobile = ?", req.Mobile).First(u).Error; err != nil {
		return c.ErrorResp(response.ResultCodeResourceError, "用户不存在")
	}
	if err = u.Compare(req.Pwd); err != nil {
		return c.ErrorResp(response.ResultCodeError, "密码错误")
	}

	t, err := c.TokenSign(u)
	if err != nil {
		return err
	}

	return c.SuccessResp(response.BuildUserAndTokenResp(u, t))
}

// UserRefreshToken 刷新 token
// @Summary 刷新 token
// @Tags user
// @Produce  json
// @Security ApiKeyAuth
// @Success 200 {object} token.Info
// @Router /cart/refresh_token [post]
func UserRefreshToken(c *context.AppContext, s string) error {
	t, err := c.TokenRefresh(s)
	if err != nil {
		return err
	}
	return c.SuccessResp(t)
}

// UserLogout 用户登出
// @Summary 用户登出
// @Tags user
// @Produce  json
// @Security ApiKeyAuth
// @Success 200 {object} response.CommonResponse
// @Router /cart/refresh_token [post]
func UserLogout(c *context.AppContext, u *models.UserInfo, s string) error {
	c.TokenForget(s)
	return c.SuccessResp(nil)
}

// UserForgetPwd 忘记密码
// @Summary 忘记密码
// @Tags cart
// @Accept  json
// @Produce  json
// @Param json body request.UserForgetPwdReq true "忘记密码信息"
// @Success 200 {object} models.UserSerializer
// @Router /user/forget_pwd [post]
func UserForgetPwd(c *context.AppContext) (err error) {
	req := new(request.UserForgetPwdReq)
	if err = c.BindAndValidate(req); err != nil {
		return err
	}

	u := new(models.UserInfo)
	if err = c.DB().Where("mobile = ?", req.Mobile).First(u).Error; err != nil {
		return c.ErrorResp(response.ResultCodeResourceError, "用户不存在")
	}

	// TODD: 处理忘记密码的逻辑

	return c.SuccessResp(u.Serialize())
}

// UserResetPwd 重置密码
// @Summary 重置密码
// @Tags user
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param json body request.UserResetPwdReq true "重置密码信息"
// @Success 200 {object} response.UserTokenResp
// @Router /user/reset_pwd [post]
func UserResetPwd(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	req := new(request.UserResetPwdReq)
	if err = c.BindAndValidate(req); err != nil {
		return err
	}

	u.Pwd = req.Pwd
	if err = u.Update(); err != nil {
		return c.ErrorResp(response.ResultCodeDatabaseError, "密码更新失败")
	}

	t, err := c.TokenSign(u)
	if err != nil {
		return err
	}

	return c.SuccessResp(response.BuildUserAndTokenResp(u, t))
}

// UserEdit 编辑用户资料
// @Summary 编辑用户资料
// @Tags user
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param json body request.UserEditReq true "用户信息"
// @Success 200 {object} models.UserSerializer
// @Router /user/edit [post]
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
		return c.ErrorResp(response.ResultCodeDatabaseError, "用户更新失败")
	}

	return c.SuccessResp(u.Serialize())
}

// UserInfo 获取用户资料
// @Summary 获取用户资料
// @Tags user
// @Produce  json
// @Security ApiKeyAuth
// @Success 200 {object} models.UserSerializer
// @Router /user/info [get]
func UserInfo(c *context.AppContext, u *models.UserInfo, s string) error {
	return c.SuccessResp(u.Serialize())
}
