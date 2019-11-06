package file

import (
	"errors"
	"ktmall/bootstrap/config"
	"ktmall/common/utils"
	"mime/multipart"
	"path"
	"strings"

	"github.com/disintegration/imaging"
)

const (
	imagesUploadFolder = "images/"
)

// SaveImage 保存图片
// filePrefix: 文件名前缀
// maxWidth: 图片最大宽度，0 为不限制
func SaveImage(f *multipart.FileHeader, folderName, filePrefix string, maxWidth int) (string, error) {
	fileName, ext := utils.CreateRandomFileName(f, filePrefix, ".png")
	folderPath := config.String("APP.UPLOAD_DIR") + folderName + utils.CreateBaseTimeFolderName()

	ext = strings.ToLower(ext)
	if ext != ".png" && ext != ".jpg" && ext != ".jpeg" && ext != ".bmp" && ext != ".gif" {
		return "", errors.New("文件格式错误，不能上传 " + ext + "格式的文件")
	}

	// 保存
	src, err := f.Open()
	if err != nil {
		return "", err
	}
	defer src.Close()

	if err := SaveFile(src, folderPath, fileName); err != nil {
		return "", err
	}

	filePath := path.Join(folderPath, fileName)
	// 需要 resize 图像
	if maxWidth > 0 {
		ReduceImageSize(filePath, maxWidth)
	}

	return config.String("APP.URL") + "/" + filePath, nil
}

// resize 图片
func ReduceImageSize(imgPath string, maxWidth int) error {
	if maxWidth <= 0 {
		return nil
	}

	img, err := imaging.Open(imgPath)
	if err != nil {
		return err
	}

	imgWidth := img.Bounds().Dx()
	if imgWidth <= maxWidth {
		return nil
	}
	// resize
	newImg := imaging.Resize(img, maxWidth, 0, imaging.Lanczos) // 等比 resize
	if err = imaging.Save(newImg, imgPath); err != nil {
		return err
	}

	return nil
}
