package token

import (
	"errors"
	"ktmall/app/models"
	"ktmall/common"
	"time"
)

// Info token info
type Info struct {
	Token     string `json:"token"`
	Type      string `json:"type"`
	ExpiresIn string `json:"expires_in"`
}

const (
	cacheTokenKeyName           = "jwt_token_"
	jwtTokenRefreshTime         = 20160 * time.Minute  // 允许刷新 token 的时间 (14 天); 期间内允许使用之前颁发的 token (即使过期)来获取新token
	jwtTokenExpiredTime         = 24 * 365 * time.Hour // token 过期时间 (1年过期)
	jwtTokenRemainTime          = 2 * time.Minute      // token 刷新后，旧的 token 还有 2 分钟的使用时间 (前提是旧 token 没过过期时间)
	tokenParamsKeyName          = "token"              // token 存在于 query formValue 中的 key name
	tokenHeaderKeyName          = "Authorization"      // header key
	tokenInHeaderIdentification = "Bearer"             // header value split
)

var (
	tokenClaimsErr   = errors.New("Token claims parse error")
	tokenExpireErr   = errors.New("Token expired")
	tokenParseErr    = errors.New("Token parse error")
	tokenBlackErr    = errors.New("Token cannot be used")
	tokenNotFoundErr = errors.New("Token not found")
)

func getCacheKey(tokenString string) string {
	return cacheTokenKeyName + tokenString
}

// Sign 签发 token
func Sign(u *models.UserInfo) (*Info, error) {
	t, claims, err := create(u)
	if err != nil || t == "" {
		return nil, errors.New("Token sign error: " + err.Error())
	}

	return &Info{
		Token:     t,
		Type:      tokenInHeaderIdentification,
		ExpiresIn: time.Unix(claims.ExpiresAt, 0).Format(common.DateTimeLayout),
	}, nil
}

// Refresh 刷新 token
func Refresh(tokenString string) (*Info, error) {
	t, claims, err := refresh(tokenString)
	if err != nil || t == "" {
		return nil, err
	}

	return &Info{
		Token:     t,
		Type:      tokenInHeaderIdentification,
		ExpiresIn: time.Unix(claims.ExpiresAt, 0).Format(common.DateTimeLayout),
	}, nil
}

// Forget 使 token 失效
func Forget(tokenString string, remainTime time.Duration) {
	forget(tokenString, remainTime)
}
