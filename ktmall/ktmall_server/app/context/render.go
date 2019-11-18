package context

import "net/http"

type TplData map[string]interface{}

func (c *AppContext) RenderHTML(tpl string, data interface{}) error {
	tplname := tpl + ".html"

	if typed, ok := data.(TplData); ok {
		return c.Render(http.StatusOK, tplname, map[string]interface{}(typed))
	}

	return c.Render(http.StatusOK, tplname, data)
}

func (c *AppContext) RenderHTMLNoData(tpl string) error {
	return c.RenderHTML(tpl, map[string]interface{}{})
}
