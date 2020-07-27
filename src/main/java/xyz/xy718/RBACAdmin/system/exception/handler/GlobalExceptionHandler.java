package cloud.catisland.ivory.system.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import cloud.catisland.ivory.system.model.BO.ResultBean;

import java.util.List;

/**
 * 全局异常捕获，一般捕获一些较常见的异常
 * @Author: Xy718
 * @Date: 2020-06-05 13:58:18
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-10 00:28:02
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 用于捕获Controller接口传入字段校验不通过的异常
     * @param exception
     * @return ResultBean
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultBean MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> allErrors=exception.getBindingResult().getAllErrors();

        return ResultBean.error(allErrors.get(0).getDefaultMessage());
    }

    /**
     * 用于捕获文件体获取不到的异常
     * @param exception
     * @return ResultBean
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultBean MultipartException(MultipartException exception) {
        return ResultBean.error("找不到上传的文件，请尝试重新上传");
    }


    /**
     * 用于捕获字段不足的异常
     * @param exception
     * @return ResultBean
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultBean MissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return ResultBean.error("没有足够的数据来交互借口，可能我代码写的有问题？？");
    }
}