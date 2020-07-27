package cloud.catisland.ivory.system.exception.base;

import lombok.Data;

/**
 * 自定义异常：
 * 用于定义用户上传文件失败的异常
 * @Author: Xy718
 * @Date: 2020-06-05 17:45:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-05 17:49:43
 */
@Data
public class FileUploadFailedException extends Exception {
    private static final long serialVersionUID = 231170670033116L;
    String message;
    public FileUploadFailedException(String message) {
        this.message="文件上传失败：";
	}
}