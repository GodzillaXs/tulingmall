package com.tulingxueyuan.mall.config;

import com.tulingxueyuan.mall.common.util.JwtTokenUtil;
import com.tulingxueyuan.mall.component.OuzoTradePay;
import com.tulingxueyuan.mall.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限验证拦截器
 */
@Configuration
public class GlobalWebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    OuzoTradePay ouzoTradePay;

    /**
     * 该拦截器主要是为了权限验证
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        将拦截器认证换为SpringSecurity方式的认证（靠过滤器）
//        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");
    }

    @Bean
    @ConfigurationProperties(prefix = "secure.ignored")
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }


    /**
     * JWT的工具类，在Common中
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }

    /**   
     * 将物理文件夹中的二维码路径映射为静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //添加一条静态资源映射规则，根据二维码的映射路径去获取磁盘物理路径的资源
        registry.addResourceHandler(ouzoTradePay.getHttpBasePath()+"/**")
                .addResourceLocations("file:"+ouzoTradePay.getStorePath()+"/");
    }
}
