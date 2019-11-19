package alipay

import (
	"bytes"
	"crypto"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
	"crypto/x509"
	"encoding/base64"
	"encoding/json"
	"encoding/pem"
	"ktmall/common"
	"ktmall/config"
	"net/url"
	"sort"
	"strconv"
	"time"
)

var (
	rsa2PrivateKey = []byte("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCF2UCdy/sKooq77KqcxWHwTnDHevz9KXJfP0h4cF1c62HtgUOp1h1EasbC4Pi8s2al2mUiqrmbBLILE3BeGU62N7Mr5b8qixwCYcGwFX7H/WSpjsoz3+yXpyPD8Zw0Me7u6sLHe5msIlClH1GvCIt7O3f1DYwi7hAGHBIhhuOef7CsymbAcWskEUfpZZG3V3xzIu6MEwUqKHxKQ+dGDxC+J+mDNxndJbgSFDr9SNaBgQeD827FGKH/pfnepG1rQl3bQjXLk8Vm2iFOPDXHnxAt7WgcVMSlKysyjvqRwK2qhB4Vreb+05bdo5lHXon4hFP6QDqyywOIZSRSjITimrA3AgMBAAECggEAEZBowkw6LTMXMYHlAZ2FNJuWgRbKjA4cTBNp1yQnnEHnH9uOnYLTZF7+piQPf/OqzKjUDLCKmqULGBj87dw0UAUPJfuIEnUev9FEMW8gq+28f+OU9Sm1SlFMiDH0ZYUH7aIw3SyDBeua2ZA6j9V3ODydiTK16P+EasjUOEITLIE0FJLhSZZlKlOVyDsbrWe8gaKlehd+n4HIWuTupH5/l4Zc7dIhDdEh3ibTvYGWt390sTugwlHZYoKGiI5d+/kJna6OLU9jdns8FFNIDD0Op2u9ZGInNkdIkG6K7DwtMi57cEaijidIfz3kkiKG92PmduGYY5sm2uXZr3VFKlkxwQKBgQDTNy8CvSvJl0pwS18h4dvUNk1NZ2wKTjc7u0u95OQ7KSngsynpgJLyEF57kRZoiLpu4XJ4miCS+VhRqI1yUwa7wcH7GrLwqYNWSyscCYxZf5ZhzlsMkmtZ0ma0L3/ypSrbOvhuOEl3y7XJowmXHpswnjIZV8+r7Bj2PryHwjZmpQKBgQCiOpRPZlj1HYsQ5TPOHzRlhIoqaJy3+L7FTRXiIiU8R2tyLxjkKJgFnHre91mYzfcNTXp497D7mwYvJQk2Ul48Nsl2HFoFYiudEGvwjwH1Laxfmm6J3H+N/ZgZxN6TD/SZVgDgszIMxvaCPulG234KCLRfl9cSXreuwLub+hmgqwKBgFgXCtdEmG+SVqxw8vNJpIpkP2dJ/AhsSKLz4YoIXZvC9kFN1+wYALFOXGmCwGrGo6K9CiX8ehtyPQCJoLyOnh9Olx2oK/stZ6Zk4UznOo7VJJ4UF2st4fHrS4RYzy8e7bnTZWtqtFFvN5kqE0Tgse7b+58QPX/Bj0OcNLsWJ+Q1AoGBAJJHq++kY9Yxm3HSmlHkBUT8q/Dtf1LL8ojJp0/OZjDiXVoKabFhgSKeJJTkCGAmZ/wQvrxQ678T3BGqnZHj16EH7cSYb/umt5jac9nnuWYwlttlzwJ0VnfcEMg7ok6M9otE52m1aw3mYeSzgXko9Y9qpeoEaiPgUFQ86W4NqAodAoGAKtDcIsbf5iSOVhqFu2NYDZrf2AMLNIgkFNEKtf/lQGbjqSy/Nbr+pZPNDdaw2dzu2JNXZJV+Srq9rM0akRYzKIFvwW+goTstts1q1hRHlTUd5bkCHClE6I/LidX2krtHM93dDlGbpqHF8yrZwunJWDwSnMY1xydNUWJiMauv7Hk=")
)

// 构造支付订单参数
func BuildOrderParamMap(orderId uint, totalPrice float64) (url.Values, error) {
	p := url.Values{}

	p.Add("app_id", config.String("ALIPAY.APPID"))
	p.Add("method", "alipay.trade.app.pay")
	p.Add("charset", "utf-8")
	p.Add("sign_type", "RSA2")
	p.Add("timestamp", time.Now().Format(common.DateTimeLayout))
	p.Add("version", "1.0")

	bytes, err := json.Marshal(buildBizContent(orderId, totalPrice))
	if err != nil {
		return p, err
	}

	p.Add("biz_content", string(bytes))

	sign, err := getSign(p)
	if err != nil {
		return p, err
	}

	p.Add("sign", sign)

	return p, nil
}

func buildBizContent(orderId uint, totalPrice float64) map[string]string {
	return map[string]string{
		"timeout_express": "30m",
		"product_code":    "QUICK_MSECURITY_PAY",
		"total_amount":    strconv.FormatFloat(totalPrice, 'f', -1, 64),
		"subject":         "标题1",
		"body":            "我是测试数据",
		"out_trade_no":    strconv.Itoa(int(orderId)),
	}
}

func getSign(uv url.Values) (string, error) {
	var err error
	data := getSignContent(uv)

	block, _ := pem.Decode(rsa2PrivateKey)
	if block == nil {
		return "", err
	}

	privateKey, err := x509.ParsePKCS1PrivateKey(block.Bytes)
	if err != nil {
		return "", err
	}

	h := sha256.New()
	_, err = h.Write(data)
	if err != nil {
		return "", err
	}

	sign, err := rsa.SignPKCS1v15(rand.Reader, privateKey, crypto.SHA256, h.Sum(nil))
	if err != nil {
		return "", err
	}

	return base64.StdEncoding.EncodeToString(sign), nil
}

func getSignContent(uv url.Values) []byte {
	if uv == nil {
		return []byte("")
	}

	var buf bytes.Buffer
	keys := make([]string, 0, len(uv))
	for k := range uv {
		keys = append(keys, k)
	}
	sort.Strings(keys)

	for _, k := range keys {
		vs := uv[k]
		prefix := k + "="
		for _, v := range vs {
			if buf.Len() > 0 {
				buf.WriteByte('&')
			}

			buf.WriteString(prefix)
			buf.WriteString(v)
		}
	}

	return buf.Bytes()
}
