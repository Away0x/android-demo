package jpush

import (
	"fmt"
	"ktmall/config"
	"sync"

	"github.com/lexkong/log"

	jpushclient "github.com/ylywyn/jpush-api-go-client"
)

type (
	JPush struct {
		Key    string
		Secret string
	}

	JPushOptions struct {
		Platform *jpushclient.Platform
		Audience *jpushclient.Audience
		Notice   *jpushclient.Notice
		Message  *jpushclient.Message
	}
)

var (
	once  sync.Once
	jpush *JPush
)

func BuildJPushOptions() *JPushOptions {
	return &JPushOptions{}
}

func (o *JPushOptions) SetPlatform() *JPushOptions {
	pf := &jpushclient.Platform{}
	pf.AddAndrid()
	pf.AddIOS()

	o.Platform = pf
	return o
}

func (o *JPushOptions) SetAudience(ids []string) *JPushOptions {
	ad := &jpushclient.Audience{}
	ad.SetID(ids)

	o.Audience = ad
	return o
}

func (o *JPushOptions) SetNotice(alert string) *JPushOptions {
	no := &jpushclient.Notice{}
	no.SetAlert(alert)

	o.Notice = no
	return o
}

func (o *JPushOptions) SetMessage(title, content string, extras map[string]interface{}) *JPushOptions {
	msg := &jpushclient.Message{}
	msg.SetTitle(title)
	msg.SetContent(content)
	msg.Extras = extras

	o.Message = msg
	return o
}

func GetInstance() *JPush {
	// if jpush == nil {
	// 	jpush = New()
	// }
	once.Do(func() {
		jpush = New()
	})

	return jpush
}

func New() *JPush {
	return &JPush{
		Key:    config.String("JPUSH.KEY"),
		Secret: config.String("JPUSH.SECRET"),
	}
}

func (j *JPush) Send(opts *JPushOptions) {
	if j.Key == "" || j.Secret == "" {
		log.Debug("common.jpush#Send key or secret config not found")
		return
	}

	payload := jpushclient.NewPushPayLoad()

	payload.SetPlatform(opts.Platform)
	payload.SetAudience(opts.Audience)
	payload.SetNotice(opts.Notice)
	payload.SetMessage(opts.Message)

	bytes, _ := payload.ToBytes()
	c := jpushclient.NewPushClient(j.Secret, j.Key)
	s, e := c.Send(bytes)
	fmt.Println(s, e)
}
