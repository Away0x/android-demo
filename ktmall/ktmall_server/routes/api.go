package routes

import (
	"ktmall/app/context"
	. "ktmall/app/controllers"
	"ktmall/routes/wrapper"

	"github.com/labstack/echo/v4"
)

const (
	APIPrefix = "/api"
)

func registerAPI(e *echo.Echo) {
	ee := e.Group(APIPrefix)

	context.RegisterHandler(ee.GET, "/upload_token", wrapper.GetTokenAndUser(CommonGetQiNiuUploadToken))

	user := ee.Group("/user")
	{
		context.RegisterHandler(user.POST, "/register", UserRegister)
		context.RegisterHandler(user.POST, "/login", UserLogin)
		context.RegisterHandler(user.POST, "/logout", wrapper.GetTokenAndUser(UserLogout))
		context.RegisterHandler(user.POST, "/refresh_token", wrapper.GetToken(UserRefreshToken))
		context.RegisterHandler(user.POST, "/forget_pwd", UserForgetPwd)
		context.RegisterHandler(user.POST, "/reset_pwd", wrapper.GetTokenAndUser(UserResetPwd))
		context.RegisterHandler(user.POST, "/edit", wrapper.GetTokenAndUser(UserEdit))
		context.RegisterHandler(user.GET, "/info", wrapper.GetTokenAndUser(UserInfo))
	}

	address := ee.Group("/address")
	{
		context.RegisterHandler(address.POST, "/add", wrapper.GetTokenAndUser(AddressAdd))
		context.RegisterHandler(address.POST, "/delete/:id", wrapper.GetTokenAndUser(AddressDelete))
		context.RegisterHandler(address.POST, "/modify/:id", wrapper.GetTokenAndUser(AddressModify))
		context.RegisterHandler(address.GET, "/list", wrapper.GetTokenAndUser(AddressList))
	}

	cart := ee.Group("/cart")
	{
		context.RegisterHandler(cart.GET, "/list", wrapper.GetTokenAndUser(CartList))
		context.RegisterHandler(cart.POST, "/add", wrapper.GetTokenAndUser(CartAdd))
		context.RegisterHandler(cart.POST, "/delete", wrapper.GetTokenAndUser(CartDelete))
		context.RegisterHandler(cart.POST, "/submit", wrapper.GetTokenAndUser(CartSubmit))
	}

	category := ee.Group("/category")
	{
		context.RegisterHandler(category.GET, "/list", CategoryList)
	}

	goods := ee.Group("/goods")
	{
		context.RegisterHandler(goods.GET, "/list", GoodsList)
		context.RegisterHandler(goods.GET, "/detail/:id", GoodsDetail)
	}

	message := ee.Group("/message")
	{
		context.RegisterHandler(message.GET, "/list", wrapper.GetTokenAndUser(MessageList))
	}

	order := ee.Group("/order")
	{
		context.RegisterHandler(order.POST, "/get_pay_sign", OrderGetPaySign)
		context.RegisterHandler(order.POST, "/pay", OrderPay)
		context.RegisterHandler(order.POST, "/cancel", OrderCancel)
		context.RegisterHandler(order.POST, "/confirm", OrderConfirm)
		context.RegisterHandler(order.GET, "/detail", OrderDetail)
		context.RegisterHandler(order.GET, "/list", OrderList)
		context.RegisterHandler(order.POST, "/submit", OrderSubmit)
	}
}
