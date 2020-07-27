package cloud.catisland.ivory.system.config.security.login;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import cloud.catisland.ivory.common.service.JwtUserService;
import cloud.catisland.ivory.system.model.BO.ResultBean;
import lombok.extern.slf4j.Slf4j;

/**
 * 登陆成功的处理程序
 * @Author: Xy718
 * @Date: 2020-05-24 23:28:06
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-10 16:26:50
 */
@Slf4j
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    
    private JwtUserService jwtUserService;
    
    public JsonLoginSuccessHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request
        ,HttpServletResponse response
        ,Authentication authentication
        ) throws IOException, ServletException {
        log.info("用户 {} 登录成功",((UserDetails)authentication.getPrincipal()).getUsername());
        //生成token，并把token加密相关信息缓存
        String token = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
        response.setHeader("Authorization", token);
        response.setContentType("application/json");
        HashMap<String,String> temp=new HashMap<String,String>();
        temp.put("jwt", token);
        response.getWriter()
            .write(JSON.toJSONString(ResultBean.success("登陆成功！",temp)));
    }
    
}