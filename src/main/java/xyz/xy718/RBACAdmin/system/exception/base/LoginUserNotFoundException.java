package cloud.catisland.ivory.system.exception.base;

import lombok.Data;

/**
 * 自定义异常：
 * 用于定义查找不到登录的用户的异常
 * @Author: Xy718
 * @Date: 2020-06-05 17:45:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-05 17:49:43
 */
@Data
public class LoginUserNotFoundException extends Exception {
    private static final long serialVersionUID = 2204831170670033116L;
    
    String message;

    public LoginUserNotFoundException() {
        this.message="请先登录";
	}

    
}