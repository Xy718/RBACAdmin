package cloud.catisland.ivory.system.config.security.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

import cloud.catisland.ivory.system.config.filter.SimpleUsernamePasswordFilter;

/**
 * 登录配置类
 * @Author: Xy718
 * @Date: 2020-05-24 23:28:06
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-04 13:56:03
 */
public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
        extends AbstractHttpConfigurer<T, B> {

    private SimpleUsernamePasswordFilter authFilter;

    public JsonLoginConfigurer() {
        this.authFilter = new SimpleUsernamePasswordFilter();
    }
    
    @Override
    public void configure(B http)
     throws Exception {
        //设置Filter使用的AuthenticationManager,这里取公共的即可
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置失败的Handler
        authFilter.setAuthenticationFailureHandler(new JsonLoginFailureHandler());
        //不将认证后的context放入session
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        SimpleUsernamePasswordFilter filter = postProcess(authFilter);
        //指定Filter的位置
        http.addFilterAfter(filter, LogoutFilter.class);
    }
    //设置成功的Handler，这个handler定义成Bean，所以从外面set进来
    public JsonLoginConfigurer<T,B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler){
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return this;
    }

}