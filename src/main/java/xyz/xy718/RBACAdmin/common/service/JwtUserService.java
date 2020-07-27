package cloud.catisland.ivory.common.service;

import java.util.Date;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class JwtUserService implements UserDetailsService {

	UserService uService;

	public JwtUserService(UserService uService) {
		this.uService=uService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//TODO 用户角色需要配置 
		cloud.catisland.ivory.common.dao.model.User u = uService.findByUserName(username).get();
		return User.builder()
				.username(u.getUserName())
				.password(u.getPassword())
				.roles("USER")
				.build();
	}

	public String saveUserLoginInfo(UserDetails user) {
		Optional<cloud.catisland.ivory.common.dao.model.User> u = uService.findByUserName(user.getUsername());
		//TODO 将salt保存到数据库或者缓存中
		String salt = u.isPresent()?BCrypt.gensalt():u.get().getPasswordSalt();
		//BCrypt.gensalt();  正式开发时可以调用该方法实时生成加密的salt
		//redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);	
		//TODO 这个部分的secret持争议态度
		Algorithm algorithm = Algorithm.HMAC256(u.get().getPassword());//or salt
		Date date = new Date(System.currentTimeMillis()+3600*1000);  //设置1小时后过期
        return JWT.create()
        		.withSubject(user.getUsername())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
	}

	public UserDetails getUserLoginInfo(String username) {
		// Optional<cloud.catisland.ivory.common.dao.model.User> u = uService.findByUserName(username);
		// String salt = u.isPresent()?BCrypt.gensalt():u.get().getPasswordSalt();
    	//TODO 从数据库或者缓存中取出jwt token生成时用的salt
    	// salt = redisTemplate.opsForValue().get("token:"+username); 	
    	UserDetails user = loadUserByUsername(username);
    	//将salt放到password字段返回
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())//TODO or salt
				.authorities(user.getAuthorities())
				.build();
	}

	public void deleteUserLoginInfo(String username) {
		//TODO 清除数据库或者缓存中登录salt
	}
}