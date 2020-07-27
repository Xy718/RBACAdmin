package cloud.catisland.ivory.system.exception.base;

import lombok.Data;

/**
 * 自定义异常：
 * 用于定义用户名密码错误的异常
 * @Author: Xy718
 * @Date: 2020-06-05 17:45:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-10 00:19:22
 */
@Data
public class UserPassErrorException extends Exception {
    private static final long serialVersionUID = 2204831170670033117L;
    
    String message;

    public UserPassErrorException() {
        this.message="用户名或密码错误";
	}

    
}