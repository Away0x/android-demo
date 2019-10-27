package factory

import (
	"fmt"
	"ktmall/app/models"
)

func factoryGoodsSku() {
	dropAndCreateTable(&models.GoodsSku{})

	gss := []*models.GoodsSku{
		{GoodsId: 1, Title: "版本", Content: "1.6GHz i5处理器,2017年i5处理器升级版,i7处理器 定制版"},
		{GoodsId: 1, Title: "配置", Content: "8GB内存/128GB SSD,8GB内存/256GB SSD"},
		{GoodsId: 2, Title: "配置", Content: "13.3英寸/双核 i5/8G/128G闪存,15.4英寸/四核 i7/16G/256G闪存,15.4英寸/四核 i7/16G/512G闪存"},
		{GoodsId: 3, Title: "颜色", Content: "银色,深空灰色"},
		{GoodsId: 3, Title: "配置", Content: "Core i5/8G内存/256G闪存,256G闪存/Multi-Touch Bar,512G闪存/Multi-Touch Bar"},
		{GoodsId: 4, Title: "颜色", Content: "MMGF2CH/A银色,2017款 MQD32CH/A,MMGG2CH/A银色,2017款 MQD42CH/A"},
		{GoodsId: 4, Title: "版本", Content: "Core i5/8G内存/128G闪存,Core i5/8G内存/256G闪存"},
		{GoodsId: 5, Title: "颜色", Content: "13英寸 Core i5 8G内存 256G闪存,MMGG2CH/A银色,13英寸Multi-Touch Bar 256G,15英寸 Multi-Touch Bar 512G"},
		{GoodsId: 5, Title: "版本", Content: "银色,深空灰色"},
		{GoodsId: 6, Title: "版本", Content: "1.6GHz i5处理器,2017年i5处理器升级版,i7处理器 定制版"},
		{GoodsId: 6, Title: "配置", Content: "8GB内存/128GB SSD,8GB内存/256GB SSD"},
		{GoodsId: 7, Title: "颜色", Content: "MMGF2CH/A银色,2017款 MQD32CH/A,MMGG2CH/A银色,2017款 MQD42CH/A"},
		{GoodsId: 7, Title: "版本", Content: "Core i5/8G内存/128G闪存,Core i5/8G内存/256G闪存"},
		{GoodsId: 8, Title: "颜色", Content: "银白色"},
		{GoodsId: 8, Title: "版本", Content: "8G 128G,16GB 256G"},
		{GoodsId: 9, Title: "颜色", Content: "金色,银色,深空灰,玫瑰金"},
		{GoodsId: 9, Title: "版本", Content: "32GB,128GB"},
		{GoodsId: 10, Title: "颜色", Content: "亮黑色,金色,银色,深空灰,玫瑰金"},
		{GoodsId: 10, Title: "版本", Content: "32GB,128GB,256GB"},
		{GoodsId: 11, Title: "颜色", Content: "灰色,金色"},
		{GoodsId: 12, Title: "颜色", Content: "【i7豪华版】8G 1T,【i7基础版】4G 500G,【i7顶配版】8G 256GSSD,【爆款】180开合i5 4G 500G,【高配款】180开合i5 8G 500G"},
		{GoodsId: 13, Title: "颜色", Content: "【轻薄特价】180打开i5标配,【180打开 触控】i5 8G 180G,【180打开背光键盘】i5 8G 256G,【碳纤维360度窄边框】180G,【黑色180开合】i7极速"},
		{GoodsId: 14, Title: "颜色", Content: "【黑侠游戏本特价款】GTX950,【黑侠游戏本高配款】GTX950,【黑侠游戏本顶配款】GTX950,【黑侠游戏i7殿堂款】GTX950"},
		{GoodsId: 15, Title: "颜色", Content: "2017新X1 8G 256G,2017新X1 i7 8G 256G,2017新X1 i7 8G 512G,经典款 8G 180G"},
	}

	for _, g := range gss {
		if err := models.DB().Create(&g).Error; err != nil {
			fmt.Printf("mock goods_sku error： %v\n", err)
		}
	}
}
