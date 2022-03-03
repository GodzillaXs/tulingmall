package com.tulingxueyuan.config;

import com.tulingxueyuan.component.JwtAuthenticationFilter;
import com.tulingxueyuan.component.RestfulAccessDeniedHandler;
import com.tulingxueyuan.component.RestfulAuthenticationEntryPoint;
import com.tulingxueyuan.component.SecurityResourceRoleSource;
import com.tulingxueyuan.component.dynamicSecurity.DynamicAccessDecisionManager;
import com.tulingxueyuan.component.dynamicSecurity.DynamicSecurityFilter;
import com.tulingxueyuan.component.dynamicSecurity.DynamicSecurityMetadataSource;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/3 15:01
 * @Version 1.0
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    //加上required = false，代表这个bean如果有配置，就自动注入，没有配置就忽略
    //主要是为了后台配置权限与资源，前台并不需要分配，所以需要区分开来（静态）
    @Autowired(required = false)
    private SecurityResourceRoleSource securityResourceRoleSource;

    //动态
    @Autowired(required = false)
    private DynamicSecurityMetadataSource dynamicSecurityService;

    /**
     * 资源路径拦截配置(专业说法叫权限配置)
     *
     * @Date 2021/12/3 15:16
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //白名单的资源路径放行
        for (String url : ignoredUrlsConfig().getUrls()) {
            registry.antMatchers(url).permitAll(); //放行
        }
        //registry.antMatchers("/pms/**").hasAuthority("Ouzo"); 只有有Ouzo这个权限的用户才能访问/pms资源路径下的请求接口
        //配置资源和权限之间的关系（静态，只在服务器启动时执行一次）
        if (securityResourceRoleSource!=null){
            Map<String, List<String>> resourceRoleMap = securityResourceRoleSource.getResourceRole();
            for (String resource : resourceRoleMap.keySet()) {
                //需要将list转换为字符串类型的数组
                List<String> list = resourceRoleMap.get(resource);
                String[] roleNameArray = list.toArray(new String[list.size()]);
                registry.antMatchers(resource) //资源
                        //hasAnyAuthority第二个参数为可变长度参数（String...），其实就是一个字符串数组
                        .hasAnyAuthority(roleNameArray); //数组中任一权限都可以访问该资源
            }
        }

        //允许浏览器的OPTIONS请求 (CORS)
        registry.antMatchers(HttpMethod.OPTIONS).permitAll(); //放行
        //其他请求都需登录才可访问
        registry.anyRequest().authenticated();
        // 关闭csrf跨站请求伪造 :因为现在使用jwt来实现认证
        //registry的and()可以反过来返回HttpSecurity对象，所以也可以用registry.and().csrf().disable();
        http.csrf().disable();
        http.cors(); //支持跨域
        //禁用session,因为用jwt实现认证用不到，禁掉可以提升服务器性能
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 自定义权限拒绝处理类
        http.exceptionHandling()
            // 没有权限访问时的处理类
            .accessDeniedHandler(restfulAccessDeniedHandler())
            // 没有登录时的处理类
            .authenticationEntryPoint(restfulAuthenticationEntryPoint());
        //将实现jwt认证的过滤器插入在SpringSecurity过滤器链的第一个位置，也就是放在原先排第一的UsernamePasswordAuthenticationFilter的前面
        //这样后面的过滤器就都能拿到认证信息，不会把登录过的也给拦截住
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //有动态权限配置时添加动态权限校验过滤器（动态，权限有变动时会重新去数据库读取数据）
        if(dynamicSecurityService!=null){
            registry.and().addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
        }
    }

    /**
     * 密码加密器
     * @Date 2021/12/5 12:31
     * @return org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public IgnoredUrlsConfig ignoredUrlsConfig(){
        return new IgnoredUrlsConfig();
    }
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }
    @Bean
    public RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint(){
        return new RestfulAuthenticationEntryPoint();
    }


    /** 以下方法都是在实现动态权限配置时加入
     * 作用：根据当前请求url获取对应角色
     * @return
     */
    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    /**
     * 作用：在FilterSecurityInterceptor前面的自定义过滤器
     * @return
     */
    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }

    /**
     * 作用：鉴权
     * @return
     */
    @ConditionalOnBean(name = "dynamicSecurityService")
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }
}
