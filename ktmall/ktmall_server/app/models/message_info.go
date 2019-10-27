package models

const (
	MessageInfoTableName = "message_info"
)

// 消息
type MessageInfo struct {
	BaseModel
	Icon    string
	Title   string
	Content string
	Time    string
	UserId  uint
}

func (MessageInfo) TableName() string {
	return MessageInfoTableName
}
