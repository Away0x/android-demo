package models

import (
	"ktmall/common/serializer"
)

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

func (m *MessageInfo) Serialize() serializer.Data {
	return serializer.Data{
		"icon":    m.Icon,
		"title":   m.Title,
		"content": m.Content,
		"time":    m.Time,
	}
}
