package cloud.catisland.ivory.system.config.security.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import cloud.catisland.ivory.system.model.BO.ResultBean;
import lombok.extern.slf4j.Slf4j;

/**
 * Json登陆失败处理程序
 * @Author: Xy718
 * @Date: 2020-05-24 23:28:06
 * @LastEditors: Xy718
 * @LastEditTime: 2020-07-01 09:57:55
 */
@Slf4j
public class JsonLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request
        , HttpServletResponse response
        ,AuthenticationException exception
            ) throws IOException, ServletException {
        log.info(request.getRemoteAddr()+" UNAUTHORIZED "+exception.getMessage());
        // response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter()
            .write(JSON.toJSONString(ResultBean.error("登陆失败，用户名或密码不正确")));
    }   
}