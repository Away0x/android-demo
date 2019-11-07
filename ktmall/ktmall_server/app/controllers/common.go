package handler

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/bootstrap/config"
	"ktmall/common"

	"github.com/qiniu/api.v7/v7/auth"
	"github.com/qiniu/api.v7/v7/storage"
)

/// 通用接口

const (
	qiniuUploadBucket = "ktmall"
)

// 获取七牛云上传凭证
func CommonGetQiNiuUploadToken(c *context.AppContext, u *models.UserInfo, s string) error {
	qiniuUploadAccessKey := config.String("QINIU.ACCESS_KEY")
	qiniuUploadSecretKey := config.String("QINIU.SECRET_KEY")
	if qiniuUploadAccessKey == "" || qiniuUploadSecretKey == "" {
		return c.ErrorResp(common.ResultCodeError, "qiniu accessKey or secretKey not found.")
	}

	putPolicy := storage.PutPolicy{Scope: qiniuUploadBucket}
	mac := auth.New(qiniuUploadAccessKey, qiniuUploadSecretKey)
	upToken := putPolicy.UploadToken(mac)

	return c.SuccessResp(upToken)
}
