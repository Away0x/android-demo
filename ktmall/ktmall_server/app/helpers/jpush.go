package helpers

import (
	"ktmall/common/jpush"
)

func LoginPush(pushId string) {
	opts := jpush.BuildJPushOptions().
		SetPlatform().
		SetAudience([]string{pushId}).
		SetMessage("", "登录", map[string]interface{}{
			"code": 1,
		})

	jpush.GetInstance().Send(opts)
}

func OrderPush(pushId, orderId string) {
	opts := jpush.BuildJPushOptions().
		SetPlatform().
		SetAudience([]string{pushId}).
		SetNotice("订单")

	jpush.GetInstance().Send(opts)
}
