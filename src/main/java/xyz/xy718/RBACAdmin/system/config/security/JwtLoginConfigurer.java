package cloud.catisland.ivory.system.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import cloud.catisland.ivory.system.config.filter.JwtAuthenticationFilter;
import cloud.catisland.ivory.system.config.security.login.JsonLoginFailureHandler;

public class JwtLoginConfigurer<T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
        extends AbstractHttpConfigurer<T, B> {
    
    private JwtAuthenticationFilter authFilter;
    
    public JwtLoginConfigurer() {
        this.authFilter = new JwtAuthenticationFilter();
    }
    
    @Override
    public void configure(B http) throws Exception {
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authFilter.setAuthenticationFailureHandler(new JsonLoginFailureHandler());
        //TODO 添加匿名访问url
        //将filter放到logoutFilter之前
        JwtAuthenticationFilter filter = postProcess(authFilter);
        http.addFilterBefore(filter, LogoutFilter.class);
    }
    //设置匿名用户可访问url
    public JwtLoginConfigurer<T, B> permissiveRequestUrls(String ... urls){
        authFilter.setPermissiveUrl(urls);
        return this;
    }
    
    public JwtLoginConfigurer<T, B> tokenValidSuccessHandler(AuthenticationSuccessHandler successHandler){
        authFilter.setAuthenticationSuccessHandler(successHandler);
        return this;
    }
    
}