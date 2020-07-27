package cloud.catisland.ivory.system.controller;

import java.io.Console;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.catisland.ivory.common.dao.model.User;
import cloud.catisland.ivory.common.dao.model.enums.UserRegStatus;
import cloud.catisland.ivory.common.service.JwtUserService;
import cloud.catisland.ivory.common.service.UserService;
import cloud.catisland.ivory.common.util.JwtAuthenticationToken;
import cloud.catisland.ivory.system.exception.base.UserAlreadyRegisteredException;
import cloud.catisland.ivory.system.model.BO.RegBO;
import cloud.catisland.ivory.system.model.BO.ResultBean;
import cloud.catisland.ivory.system.model.pojo.JWTModel;

/**
 * @Author: Xy718
 * @Date: 2020-05-25 23:15:14
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-05 15:18:08
 */
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    UserService uService;

    @PostMapping("/reg")
    public ResultBean reg(
        @Valid @RequestBody RegBO regBO
        ) throws UserAlreadyRegisteredException {

        LOGGER.info("用户名 {} 的注册状态:{}",regBO.getUsername(),uService.checkUserRegedByUsername(regBO.getUsername()).name());
        if(uService.checkUserRegedByUsername(regBO.getUsername()).equals(UserRegStatus.REGED)){
            throw new UserAlreadyRegisteredException(regBO);
        }
        Optional<User> u = uService.registUser(regBO);
        LOGGER.info("注册成功？"+u.isPresent());
        return u.isPresent()?
            ResultBean.success("注册成功！",u.get()):
            ResultBean.error("注册失败！Save错误");
            
    }
    
    /**
     * 
     * @param tokenModel
     * @return
     */
    @PostMapping("/verifytoken")
    public ResultBean verifytoken(
        @RequestBody JWTModel tokenModel
        ){
        Map<String,Boolean> m=new HashMap<String,Boolean>();
        Boolean isvered=false;
        try {
            DecodedJWT jwt = JWT.decode(tokenModel.getJwt());
            Optional<User> u=uService.findByUserName(jwt.getSubject());
            Algorithm algorithm = Algorithm.HMAC256(u.isPresent()?u.get().getPassword():"");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(jwt.getSubject())
                    .build();
            verifier.verify(tokenModel.getJwt());
            if(jwt.getExpiresAt().after(Calendar.getInstance().getTime())){
                isvered=true;
            }
            isvered=true;
        } catch (Exception e) {
            //校验失败
            isvered=false;
        }
        m.put("verified",isvered);
        return ResultBean.success("",m);
    }
}