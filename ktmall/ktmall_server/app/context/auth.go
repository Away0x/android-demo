package context

import (
	"ktmall/app/auth/token"
	"ktmall/app/models"
	"time"
)

// 签发 token
func (c *AppContext) TokenSign(u *models.UserInfo) (info *token.Info, err error) {
	if info, err = token.Sign(u); err != nil {
		return nil, err
	}
	return
}

// 刷新 token
func (c *AppContext) TokenRefresh(t string) (info *token.Info, err error) {
	if info, err = token.Refresh(t); err != nil {
		return nil, err
	}
	return
}

// 使 token 失效
func (c *AppContext) TokenForget(t string) {
	token.Forget(t, time.Duration(0))
}
