package cloud.catisland.ivory.system.exception.base;

import cloud.catisland.ivory.system.model.BO.RegBO;
import lombok.Data;

/**
 * 自定义异常：
 * 用于定义用户已被注册的抛出的异常
 * @Author: Xy718
 * @Date: 2020-06-05 17:45:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-05 17:49:43
 */
@Data
public class UserAlreadyRegisteredException extends Exception {
    private static final long serialVersionUID = 2204831170670033116L;
    
    String message;

    public UserAlreadyRegisteredException(RegBO regBO) {
        this.message="该用户已被注册！";
	}

    
}