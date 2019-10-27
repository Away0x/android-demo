package token

import (
	"ktmall/app/models"
	"ktmall/bootstrap/config"
	"ktmall/common/cache"
	"time"

	"github.com/dgrijalva/jwt-go"
)

// ParseToken 解析 token
func ParseToken(tokenString string) (*CustomClaims, error) {
	token, err := parse(tokenString)
	if err != nil {
		// token 过期
		if isExpired(err) {
			if claims, ok := token.Claims.(*CustomClaims); ok {
				return claims, tokenExpireErr
			}
		}

		return nil, err
	}

	if claims, ok := token.Claims.(*CustomClaims); ok && token.Valid {
		return claims, nil
	}

	return nil, tokenParseErr
}

// 创建 token
func create(u *models.UserInfo) (string, CustomClaims, error) {
	claims := CustomClaims{}
	claims.SetUser(u)
	claims.SetExpiredAt()

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	s, err := token.SignedString([]byte(config.String("APP.KEY")))
	if err != nil {
		return "", claims, err
	}
	return s, claims, nil
}

// 刷新 token
func refresh(tokenString string) (string, CustomClaims, error) {
	token, err := parse(tokenString)
	if err != nil {
		// 非过期错误
		if !isExpired(err) {
			return "", CustomClaims{}, err
		}
		// 判断是否过了可刷新时间
		if claims, ok := token.Claims.(*CustomClaims); ok {
			now := time.Now().Unix()
			if now > claims.RefreshTime {
				return "", CustomClaims{}, err
			}
		}
	}

	claims, ok := token.Claims.(*CustomClaims)
	if !ok {
		return "", CustomClaims{}, tokenClaimsErr
	}

	forget(tokenString, jwtTokenRemainTime) // 将之前的 token 加入黑名单使之失效
	u := &models.UserInfo{}
	u.ID = claims.UserID
	newToken, newClaims, err := create(u)
	if err != nil {
		return "", CustomClaims{}, err
	}

	return newToken, newClaims, nil
}

// 使 token 失效
func forget(tokenString string, remainTime time.Duration) {
	now := time.Now()
	// val 为 token 还可以使用的过渡时间
	cache.PutInt64(getCacheKey(tokenString), now.Add(remainTime).Unix(), jwtTokenExpiredTime)
}

func parse(tokenString string) (*jwt.Token, error) {
	token, err := jwt.ParseWithClaims(tokenString, &CustomClaims{}, func(token *jwt.Token) (interface{}, error) {
		return []byte(config.String("APP.KEY")), nil
	})

	// 在黑名单中
	if t, ok := cache.GetInt64(getCacheKey(tokenString)); ok {
		now := time.Now().Unix()
		// 过了留存时间了
		if now > t {
			return nil, tokenBlackErr
		}
	}

	return token, err
}

// 判断是否过期
func isExpired(err error) bool {
	switch err.(type) {
	case *jwt.ValidationError:
		vErr := err.(*jwt.ValidationError)
		switch vErr.Errors {
		case jwt.ValidationErrorExpired:
			// token 过期了
			return true
		default:
			return false
		}

	default:
		return false
	}
}
