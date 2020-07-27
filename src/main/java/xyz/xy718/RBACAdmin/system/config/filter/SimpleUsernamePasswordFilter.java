package cloud.catisland.ivory.system.config.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

public class SimpleUsernamePasswordFilter extends AbstractAuthenticationProcessingFilter {

    public SimpleUsernamePasswordFilter() {
         //拦截url为 "/login" 的POST请求
        super(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request
        , HttpServletResponse response
    )throws AuthenticationException, IOException, ServletException {
        //从json中获取username和password
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String username = null, password = null;
        if(StringUtils.hasText(body)) {
            JSONObject jsonObj = JSON.parseObject(body);
            username = jsonObj.getString("username");
            password = jsonObj.getString("password");
        }   
        
        Objects.requireNonNull(username, "用户名不能为空");
        Objects.requireNonNull(password, "密码不能为空");

        username = username.trim();
       //封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}