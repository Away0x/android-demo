package models

import (
	"errors"
	"ktmall/common/serializer"
	"ktmall/common/utils"

	"github.com/jinzhu/gorm"
)

const (
	UserInfoTableName = "user_info"
)

// 用户表
type UserInfo struct {
	BaseModel
	Name         string `sql:"comment:'用户名（登录名）'"`
	Pwd          string `sql:"comment:'登录密码'"`
	Mobile       string `sql:"comment:'手机号',unique"`
	Icon         string `sql:"comment:'头像'"`
	RealName     string `sql:"comment:'真实姓名'"`
	IdentityCard string `sql:"comment:'身份证'"`
	NickName     string `sql:"comment:'昵称'"`
	Gender       string `sql:"comment:'性别'"`
	Birthday     string `sql:"comment:'生日'"`
	Address      string `sql:"comment:'居住地）'"`
	Sign         string `sql:"comment:'个性签名'"`
	PushId       string `sql:"comment:'推送 id'"`
}

func (UserInfo) TableName() string {
	return UserInfoTableName
}

func (u *UserInfo) Serialize() serializer.Data {
	return serializer.Data{
		"id":     u.IDString(),
		"icon":   u.Icon,
		"name":   u.Name,
		"gender": u.Gender,
		"mobile": u.Mobile,
		"sign":   u.Sign,
	}
}

func (u *UserInfo) BeforeCreate(scope *gorm.Scope) (err error) {
	if u.Pwd != "" {
		if !passwordEncrypted(u.Pwd) {
			pwd, err := u.Encrypt()
			if err != nil {
				return errors.New("User Model 创建失败: passwordEncrypted")
			}
			scope.SetColumn("pwd", pwd)
		}
	}

	return err
}

func (u *UserInfo) BeforeUpdate(scope *gorm.Scope) (err error) {
	if !passwordEncrypted(u.Pwd) {
		pwd, err := u.Encrypt()
		if err != nil {
			return errors.New("UserInfo Model 更新失败")
		}
		scope.SetColumn("pwd", pwd)
	}

	return
}

// 判断密码是否加密过了
func passwordEncrypted(pwd string) (status bool) {
	return len(pwd) == 60 // 长度等于 60 说明加密过了
}

// Encrypt 对密码进行加密
func (u *UserInfo) Encrypt() (pwd string, err error) {
	pwd, err = utils.Encrypt(u.Pwd)
	u.Pwd = pwd
	return
}

// Compare 验证用户密码
func (u *UserInfo) Compare(pwd string) (err error) {
	err = utils.Compare(u.Pwd, pwd)
	return
}
