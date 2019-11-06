package file

import (
	"io"
	"io/ioutil"
	"os"
	"path"
)

// IsExist 判断所给路径文件/文件夹是否存在
func IsExist(path string) bool {
	_, err := os.Stat(path)
	if err == nil {
		return true
	}

	if os.IsNotExist(err) {
		return false
	}

	return false
}

// CreateDir 会递归创建文件夹
func CreateDir(dirPath string) error {
	if !IsExist(dirPath) {
		return os.MkdirAll(dirPath, os.ModePerm)
	}

	return nil
}

// SaveFile 保存文件
func SaveFile(f io.Reader, filePath, fileName string) error {
	if err := CreateDir(filePath); err != nil {
		return err
	}

	out, err := os.Create(path.Join(filePath, fileName))
	if err != nil {
		return err
	}
	defer out.Close()

	if _, err = io.Copy(out, f); err != nil {
		return err
	}

	return nil
}

// ReadFile 读取文件内容
func ReadFile(filePath string) (string, error) {
	f, err := os.Open(filePath)
	if err != nil {
		return "", err
	}
	defer f.Close()

	bs, err := ioutil.ReadAll(f)
	if err != nil {
		return "", err
	}

	return string(bs), nil
}
