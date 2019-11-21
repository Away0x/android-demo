package api_test

import (
	"ktmall/bootstrap"
	"ktmall/test"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestAddressList(t *testing.T) {
	test.ControllerTestInit()
	assert := assert.New(t)
	router := bootstrap.SetupServer()

	w := httptest.NewRecorder()

	req, _ := http.NewRequest(http.MethodGet, "/api/address/list", nil)

	router.ServeHTTP(w, req)

	assert.Equal(http.StatusOK, w.Code)
}
