package models

const (
	UserInfoTableName = "user_info"
)

// 用户表
type UserInfo struct {
	BaseModel
	Name         string `sql:"comment:'用户名（登录名）'"`
	Pwd          string `sql:"comment:'登录密码'"`
	Mobile       string `sql:"comment:'手机号'"`
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
