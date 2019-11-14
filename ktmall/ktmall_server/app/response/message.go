package response

import (
	"ktmall/app/models"
)

type MessageListResp []models.MessageSerializer

func BuildMessageListResp(ms []*models.MessageInfo) (list MessageListResp) {
	list = make(MessageListResp, len(ms))
	for i, o := range ms {
		list[i] = o.Serialize()
	}
	return
}
