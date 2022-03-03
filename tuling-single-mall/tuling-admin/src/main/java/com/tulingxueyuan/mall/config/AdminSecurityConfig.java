package com.tulingxueyuan.mall.config;

import com.tulingxueyuan.component.SecurityResourceRoleSource;
import com.tulingxueyuan.component.dynamicSecurity.DynamicSecurityService;
import com.tulingxueyuan.config.SecurityConfig;
import com.tulingxueyuan.mall.dto.ResourceRoleDTO;
import com.tulingxueyuan.mall.modules.ums.model.UmsResource;
import com.tulingxueyuan.mall.modules.ums.service.UmsAdminService;
import com.tulingxueyuan.mall.modules.ums.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 使用SpringSecurity进行权限认证管理
 * @Author 86131
 * @Date 2021/12/5 12:58
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity //开启Security
public class AdminSecurityConfig extends SecurityConfig {
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private UmsResourceService umsResourceService;

    //便于获取当前用户信息
    @Bean
    public UserDetailsService userDetailsService(){
//        lambda语法  此方法返回值是UserDetails接口类型，实际上是实现类AdminDetails对象
        return username -> umsAdminService.loadUserByUsernameF(username);
    }

    //获取所有 资源以及对应的角色列表,配置权限与资源
//    @Bean （静态）取消静态或动态都只需要注释对应的Bean注解即可
    public SecurityResourceRoleSource securityResourceRoleSource(){
        return () -> {
            //调用业务逻辑类查询资源对应的用户信息
            List<ResourceRoleDTO> list=umsResourceService.getSourceRole();
            Map<String,List<String>> map=new HashMap<>();
            for (ResourceRoleDTO resourceRoleDTO : list) {
                List<String> roleNameList = resourceRoleDTO.getRoleList()
                //jdk8的新用法，三个方法组合起来的作用是将集合中对象元素的某一属性值取出重新形成一个只含该属性值的list
                                                    .stream()
                                                    .map(role -> role.getName())
                                                    .collect(Collectors.toList());
                map.put(resourceRoleDTO.getUrl(),roleNameList);
            }
            return map;
        };
    }

    // 获取最新的资源角色信息（动态）
    @Bean("dynamicSecurityService")
    public DynamicSecurityService dynamicSecurityService(UmsResourceService umsResourceService) {
        return () -> {
            Map<RequestMatcher, List<ConfigAttribute>> map = new ConcurrentHashMap<>();

            List<ResourceRoleDTO> list=umsResourceService.getSourceRole();
            for (ResourceRoleDTO resource : list) {
                // 通配符匹配器
                map.put(new AntPathRequestMatcher(resource.getUrl()),
                        // 所有角色信息
                        resource.getRoleList().stream()
                                .map(role-> new org.springframework.security.access.SecurityConfig(role.getName()))
                                .collect(Collectors.toList())
                );
            }
            return map;
        };
    }
}
