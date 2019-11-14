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

type MessageSerializer struct {
	Icon    string `json:"icon"`
	Title   string `json:"title"`
	Content string `json:"content"`
	Time    string `json:"time"`
}

func (MessageInfo) TableName() string {
	return MessageInfoTableName
}

func (m *MessageInfo) Serialize() MessageSerializer {
	return MessageSerializer{
		Icon:    m.Icon,
		Title:   m.Title,
		Content: m.Content,
		Time:    m.Time,
	}
}
