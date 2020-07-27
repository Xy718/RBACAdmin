package xyz.xy718.RBACAdmin.system.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import cloud.catisland.ivory.common.service.JwtUserService;
import cloud.catisland.ivory.common.service.UserService;
import cloud.catisland.ivory.system.config.filter.OptionsRequestFilter;
import cloud.catisland.ivory.system.config.security.JwtAuthenticationProvider;
import cloud.catisland.ivory.system.config.security.JwtLoginConfigurer;
import cloud.catisland.ivory.system.config.security.JwtRefreshSuccessHandler;
import cloud.catisland.ivory.system.config.security.TokenClearLogoutHandler;
import cloud.catisland.ivory.system.config.security.login.JsonLoginConfigurer;
import cloud.catisland.ivory.system.config.security.login.JsonLoginSuccessHandler;

/**
 * SpringSecurity配置程序
 * @Author: Xy718
 * @Date: 2020-05-25 21:13:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-04 14:05:17
 */
@EnableWebSecurity
// @DependsOn("userService")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserService userService;

	@Override
    public void configure(WebSecurity web) throws Exception {
        // web.ignoring().antMatchers("/topic/**");
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http		//TODO 所有的url过滤链
			.authorizeRequests()
		        .antMatchers("/image/**").permitAll()
		        .antMatchers("/topic/**").permitAll()
		        .antMatchers("/user/**").permitAll()
		        .antMatchers("/auth/**").permitAll()
		        .antMatchers("/admin/**").hasAnyRole("ADMIN")
		        .antMatchers("/article/**").hasRole("USER")
		        .anyRequest().authenticated()
				.and()
				
		    .csrf().disable()									//关闭csrf
		    .formLogin().disable()								//关闭原生form表单登录
		    .sessionManagement().disable()						//关闭session管理
		    .cors()												//跨域支持
			.and()
			//定义统一headers
		    .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
		    		new Header("Access-control-Allow-Origin","*"),
		    		new Header("Access-Control-Expose-Headers","Authorization"))))
			.and()
			//开始添加过滤链处理程序
		    .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
			.apply(new JsonLoginConfigurer<>())
			.loginSuccessHandler(jsonLoginSuccessHandler())
			.and()
			
			.apply(new JwtLoginConfigurer<>())
			.tokenValidSuccessHandler(jwtRefreshSuccessHandler())
			.permissiveRequestUrls("/logout")
			.and()
			
		    .logout()
			//.logoutUrl("/logout")   //默认就是"/logout"
			.addLogoutHandler(tokenClearLogoutHandler())						//退出登录的处理程序
			.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())//退出登录成功的处理程序
			
			;
			// .and()
			
			// .sessionManagement().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(daoAuthenticationProvider())
		.authenticationProvider(jwtAuthenticationProvider())
		;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Bean("jwtAuthenticationProvider")
	protected AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider(jwtUserService());
	}
	
	/**
	 * 声明处理用户名密码登录的Provider
	 */
	@Bean("daoAuthenticationProvider")
	protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
		//这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsService());
		return daoProvider;
	}
	@Override
	protected UserDetailsService userDetailsService() {
		return new JwtUserService(userService);
	}
	
	@Bean("jwtUserService")
	protected JwtUserService jwtUserService() {
		return new JwtUserService(userService);
	}
	
	@Bean
	protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
		return new JsonLoginSuccessHandler(jwtUserService());
	}
	
	@Bean
	protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
		return new JwtRefreshSuccessHandler(jwtUserService());
	}
	
	@Bean
	protected TokenClearLogoutHandler tokenClearLogoutHandler() {
		return new TokenClearLogoutHandler(jwtUserService());
	}
	
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD", "OPTION"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}