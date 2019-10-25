package handler

/// 用户相关接口

// 用户注册
func UserRegister(c *AppContext) error {
	return nil
}

// 用户登录
func UserLogin(c *AppContext) error {
	return nil
}

// 忘记密码
func UserForgetPwd(c *AppContext) error {
	return nil
}

// 重置密码
func UserResetPwd(c *AppContext) error {
	return nil
}

// 编辑用户资料
func UserEdit(c *AppContext) error {
	return nil
}

// 获取用户资料
func UserInfo(c *AppContext) error {
	return c.Test()
}
