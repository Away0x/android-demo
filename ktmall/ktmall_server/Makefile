APP_NAME = "ktmall_server"
PROXY = "https://goproxy.cn"

install:
	env GOPROXY=${PROXY} go mod download

dev:
	fresh -c ./fresh.conf

help:
	@echo "make install - install dependencies"
	@echo "make dev - run go fresh"