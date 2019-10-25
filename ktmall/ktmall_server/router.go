package main

import (
	. "ktmall/handler"

	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
)

func routerRegister(e *echo.Echo) {
	e.Use(ContextMiddleware)
	e.Use(middleware.Recover())
	e.Use(middleware.LoggerWithConfig(middleware.LoggerConfig{
		Format: "${status}   ${method}   ${uri}\n",
	}))
	e.Use(middleware.CORS())

	ContextRegisterHandler(e.GET, "/upload_token", CommonGetQiNiuUploadToken)

	user := e.Group("/user")
	{
		ContextRegisterHandler(user.POST, "/register", UserRegister)
		ContextRegisterHandler(user.POST, "/login", UserLogin)
		ContextRegisterHandler(user.POST, "/forget_pwd", UserForgetPwd)
		ContextRegisterHandler(user.POST, "/reset_pwd", UserResetPwd)
		ContextRegisterHandler(user.POST, "/edit", UserEdit)
		ContextRegisterHandler(user.GET, "/info", UserInfo)
	}

	address := e.Group("/address")
	{
		ContextRegisterHandler(address.POST, "/add", AddressAdd)
		ContextRegisterHandler(address.POST, "/delete", AddressDelete)
		ContextRegisterHandler(address.POST, "/modify", AddressModify)
		ContextRegisterHandler(address.GET, "/list", AddressList)
	}

	cart := e.Group("/cart")
	{
		ContextRegisterHandler(cart.GET, "/list", CartList)
		ContextRegisterHandler(cart.POST, "/add", CartAdd)
		ContextRegisterHandler(cart.POST, "/delete", CartDelete)
		ContextRegisterHandler(cart.POST, "/submit", CartSubmit)
	}

	category := e.Group("/category")
	{
		ContextRegisterHandler(category.GET, "/list", CategoryList)
	}

	goods := e.Group("/goods")
	{
		ContextRegisterHandler(goods.GET, "/list", GoodsList)
		ContextRegisterHandler(goods.GET, "/list_by_keyword", GoodsListByKeyword)
		ContextRegisterHandler(goods.GET, "/detail", GoodsDetail)
	}

	message := e.Group("/message")
	{
		ContextRegisterHandler(message.GET, "/list", MessageList)
	}

	order := e.Group("/order")
	{
		ContextRegisterHandler(order.POST, "/get_pay_sign", OrderGetPaySign)
		ContextRegisterHandler(order.POST, "/pay", OrderPay)
		ContextRegisterHandler(order.POST, "/cancel", OrderCancel)
		ContextRegisterHandler(order.POST, "/confirm", OrderConfirm)
		ContextRegisterHandler(order.GET, "/detail", OrderDetail)
		ContextRegisterHandler(order.GET, "/list", OrderList)
		ContextRegisterHandler(order.POST, "/submit", OrderSubmit)
	}
}
