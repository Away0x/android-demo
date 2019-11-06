package utils

import (
	"crypto/rand"
	r "math/rand"
	"mime/multipart"
	"path"
	"strconv"
	"time"
)

var alphaNum = []byte(`0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`)

// RandomCreateBytes generate random []byte by specify chars.
func RandomCreateBytes(n int, alphabets ...byte) []byte {
	if len(alphabets) == 0 {
		alphabets = alphaNum
	}
	var bytes = make([]byte, n)
	var randBy bool
	if num, err := rand.Read(bytes); num != n || err != nil {
		r.Seed(time.Now().UnixNano())
		randBy = true
	}
	for i, b := range bytes {
		if randBy {
			bytes[i] = alphabets[r.Intn(len(alphabets))]
		} else {
			bytes[i] = alphabets[b%byte(len(alphabets))]
		}
	}
	return bytes
}

// 生成以时间(年月/日)作为文件夹名
func CreateBaseTimeFolderName() string {
	now := time.Now()
	year := strconv.Itoa(now.Year())
	month := strconv.Itoa(int(now.Month()))
	if len(month) == 1 {
		month = "0" + month
	}
	day := strconv.Itoa(now.Day())

	return "/" + year + month + "/" + day
}

// 生成随机文件名
// file_prefix 文件名前缀
// defaultExt 默认 ext (有些文件上传时没带文件后缀)
func CreateRandomFileName(f *multipart.FileHeader, filePrefix, defaultExt string) (string, string) {
	fileName := f.Filename
	// 获取文件的后缀名，因图片从剪贴板里黏贴时后缀名为空，所以此处确保后缀一直存在
	ext := path.Ext(fileName)
	if ext == "" && defaultExt != "" {
		ext = defaultExt // 默认的后缀
	}

	// 拼接文件名，加前缀是为了增加辨析度，前缀可以是相关数据模型的 ID
	// 值如：1_1493521050_7BVc9v9ujP.png
	fileName = filePrefix + "_" + strconv.Itoa(int(time.Now().Unix())) + "_" + string(RandomCreateBytes(10)) + ext

	return fileName, ext
}
