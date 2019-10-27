package factory

import (
	"fmt"
	"ktmall/app/models"
)

func categoryFactory() {
	dropAndCreateTable(&models.Category{})

	cats := []*models.Category{
		{Name: "电脑办公", ParentId: 0},
		{Name: "手机数码", ParentId: 0},
		{Name: "男装", ParentId: 0},
		{Name: "女装", ParentId: 0},
		{Name: "家用电器", ParentId: 0},
		{Name: "食品生鲜", ParentId: 0},
		{Name: "酒水饮料", ParentId: 0},
		{Name: "玩具乐器", ParentId: 0},
		{Name: "汽车用品", ParentId: 0},
		{Name: "家具家装", ParentId: 0},
		{Name: "礼品鲜花", ParentId: 0},
		{Name: "生活旅行", ParentId: 0},
		{Name: "二手商品", ParentId: 0},
		{Name: "酒水饮料", ParentId: 0},
		{Name: "Apple", Icon: "https://img13.360buyimg.com/n7/jfs/t2590/338/4222589387/237425/25e40fb/57b12ac6N61374a75.jpg", ParentId: 1},
		{Name: "ThinkPad", Icon: "https://img14.360buyimg.com/n7/jfs/t3556/223/2158676156/110226/59267230/584b5678Nbc9f1e70.jpg", ParentId: 1},
		{Name: "惠普", Icon: "https://img10.360buyimg.com/n7/jfs/t3157/231/5756125504/98807/97ab361d/588084a1N06efb01d.jpg", ParentId: 1},
		{Name: "戴尔", Icon: "https://img14.360buyimg.com/n7/jfs/t5971/255/1793524379/148460/f42e1432/59362fc2Nf55191b9.jpg", ParentId: 1},
		{Name: "华硕", Icon: "https://img12.360buyimg.com/n7/jfs/t5878/352/2479795637/201591/d23a1872/5931182fN31cfa389.jpg", ParentId: 1},
		{Name: "神舟", Icon: "https://img13.360buyimg.com/n7/jfs/t3052/126/5163722736/217313/f05d9003/5864c7dfNcfbc5e94.jpg", ParentId: 1},
		{Name: "外星人", Icon: "https://img11.360buyimg.com/n7/jfs/t4687/130/1245474410/58260/f12a15bd/58db17dbNf5371a02.jpg", ParentId: 1},
		{Name: "微星", Icon: "https://img12.360buyimg.com/n7/jfs/t6709/106/1129266372/226149/ab5f4641/594b8094Nb07793ab.jpg", ParentId: 1},
		{Name: "宏基", Icon: "https://img11.360buyimg.com/n7/jfs/t5737/312/4822081047/162369/70bbd1b2/5954b785N1787db72.jpg", ParentId: 1},
		{Name: "雷神", Icon: "https://img11.360buyimg.com/n7/jfs/t5671/2/2653805329/277901/cf0384f7/5932281fNeb08da02.jpg", ParentId: 1},
		{Name: "Apple", Icon: "https://img14.360buyimg.com/n7/jfs/t3268/124/2646283367/114153/f5704b88/57e4a358N9ccc6645.jpg", ParentId: 2},
		{Name: "华为", Icon: "https://img10.360buyimg.com/n7/jfs/t5890/341/1320350439/127171/2f9c4ddd/592535e0N2e102c09.jpg", ParentId: 2},
		{Name: "小米", Icon: "https://img14.360buyimg.com/n7/jfs/t5215/252/15502760/100416/cb06f1da/58f709adN45511018.jpg", ParentId: 2},
		{Name: "魅族", Icon: "https://img10.360buyimg.com/n7/jfs/t4366/71/2045605853/291379/56c87b03/58ca4dc5N1c303706.jpg", ParentId: 2},
		{Name: "三星", Icon: "https://img10.360buyimg.com/n7/jfs/t5905/106/1120548052/61075/6eafa3a5/592f8f7bN763e3d30.jpg", ParentId: 2},
		{Name: "OPPO", Icon: "https://img10.360buyimg.com/n7/jfs/t5785/24/2243796048/134801/923e11/592ea14fNec6d33c4.jpg", ParentId: 2},
		{Name: "vivo", Icon: "https://img11.360buyimg.com/n7/jfs/t5998/69/1052614141/116889/2f5ba58a/592f8ed9N49d8f07b.jpg", ParentId: 2},
		{Name: "HTC", Icon: "https://img13.360buyimg.com/n7/jfs/t5659/277/3541677944/291221/28bb44f8/593e10c9Nc3783014.jpg", ParentId: 2},
		{Name: "摩托罗拉", Icon: "https://img12.360buyimg.com/n7/jfs/t3109/185/1064081632/117451/2dba5e92/57c558e2N38a9e617.jpg", ParentId: 2},
		{Name: "索尼", Icon: "https://img10.360buyimg.com/n7/jfs/t5191/190/2535818793/70090/78c559f5/591ba9f0Nd3a41fcb.jpg", ParentId: 2},
	}

	for _, c := range cats {
		if err := models.DB().Create(&c).Error; err != nil {
			fmt.Printf("mock category error： %v\n", err)
		}
	}
}
