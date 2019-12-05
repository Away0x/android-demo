APP_NAME = "ktmall"
PROXY = "https://goproxy.cn"

default:
	go build -o ${APP_NAME}
	# env GOOS=linux GOARCH=amd64 go build -o ${APP_NAME}

install:
	env GOPROXY=${PROXY} go mod download

version:
	go run main.go version

dev:
  # go get -u github.com/Away0x/fresh
	env KTMALL_APP_RUNMODE=development fresh -c ./fresh.conf

deploy:
	make && ./bin/deploy restart

api-doc:
  # go get -u github.com/swaggo/swag/cmd/swag
	swag init -g routes/api.go

mock:
	env KTMALL_APP_RUNMODE=development go run main.go mock

api-test:
	env KTMALL_APP_RUNMODE=test go test ./test/controllers/api/...

clean:
	if [ -f ${APP_NAME} ]; then rm ${APP_NAME}; fi

help:
	@echo "make - compile the source code"
	@echo "make install - install dep"
	@echo "make version - print version"
	@echo "make dev - run go fresh"
	@echo "make deploy - deploy app"
	@echo "make mock - mock data"
	@echo "make api-test - test api"
	@echo "make doc - generate swagger api docs"
	@echo "make clean - remove binary file"
