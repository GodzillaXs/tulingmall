package com.tulingxueyuan.mall.config;

import com.tulingxueyuan.config.SecurityConfig;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 使用SpringSecurity进行权限认证管理
 * @Author 86131
 * @Date 2021/12/3 15:01
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity //开启Security
public class PortalSecurityConfig extends SecurityConfig{

    @Autowired
    private MemberService memberService;

    //便于获取当前用户信息
    @Bean
    public UserDetailsService userDetailsService(){
//        lambda语法  此方法返回值是UserDetails接口类型，实际上是实现类MemberDetails对象
        return username -> memberService.loadUserByUsername(username);
    }
}
