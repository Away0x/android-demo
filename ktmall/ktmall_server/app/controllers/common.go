package controllers

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/response"
	"ktmall/config"

	"github.com/qiniu/api.v7/v7/auth"
	"github.com/qiniu/api.v7/v7/storage"
)

/// 通用接口

const (
	qiniuUploadBucket = "ktmall"
)

// CommonGetQiNiuUploadToken 获取七牛云上传凭证
// @Summary 获取七牛云上传凭证
// @Produce  json
// @Security ApiKeyAuth
// @Success 200 {string} string
// @Router /upload_token [get]
func CommonGetQiNiuUploadToken(c *context.AppContext, u *models.UserInfo, s string) error {
	qiniuUploadAccessKey := config.String("QINIU.ACCESS_KEY")
	qiniuUploadSecretKey := config.String("QINIU.SECRET_KEY")
	if qiniuUploadAccessKey == "" || qiniuUploadSecretKey == "" {
		return c.ErrorResp(response.ResultCodeError, "qiniu accessKey or secretKey not found.")
	}

	putPolicy := storage.PutPolicy{Scope: qiniuUploadBucket}
	mac := auth.New(qiniuUploadAccessKey, qiniuUploadSecretKey)
	upToken := putPolicy.UploadToken(mac)

	return c.SuccessResp(upToken)
}
