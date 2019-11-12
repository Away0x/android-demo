APP_NAME = "ktmall"
PROXY = "https://goproxy.cn"

default:
	go build -o ${APP_NAME}
	# env GOOS=linux GOARCH=amd64 go build -o ${APP_NAME}

install:
	env GOPROXY=${PROXY} go mod download

dev:
  # go get -u github.com/Away0x/fresh
	env KTMALL_APP_RUNMODE=development fresh -c ./fresh.conf

api-doc:
  # go get -u github.com/swaggo/swag/cmd/swag
	swag init -g routes/api.go

mock:
	env KTMALL_APP_RUNMODE=development go run main.go mock

clean:
	if [ -f ${APP_NAME} ]; then rm ${APP_NAME}; fi

help:
	@echo "make - compile the source code"
	@echo "make install - install dep"
	@echo "make dev - run go fresh"
	@echo "make mock - mock data"
	@echo "make doc - generate swagger api docs"
	@echo "make clean - remove binary file"
