package org.demos.sec;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 所有请求均可访问
        http.authorizeRequests().antMatchers("/favicon.ico", "/test/*")
                .permitAll();

        // 其余所有请求均需要权限
        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin();

        // 配置登录页面的表单 action 必须是 '/login', 用户名和密码的参数名必须是 'username' 和 'password'，
        // 登录失败的 url 是 '/login-error'
        // http.formLogin().loginPage("/login").loginProcessingUrl("/login")
        // .usernameParameter("username").passwordParameter("password")
        // .failureUrl("/login-error");
    }
}
