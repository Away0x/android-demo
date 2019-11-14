package response

import (
	"ktmall/app/auth/token"
	"ktmall/app/models"
)

type UserTokenResp struct {
	Token *token.Info           `json:"token"`
	User  models.UserSerializer `json:"user"`
}

func BuildUserAndTokenResp(m *models.UserInfo, t *token.Info) UserTokenResp {
	return UserTokenResp{
		Token: t,
		User:  m.Serialize(),
	}
}
