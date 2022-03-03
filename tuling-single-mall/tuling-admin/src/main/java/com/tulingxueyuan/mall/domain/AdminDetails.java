package com.tulingxueyuan.mall.domain;

import com.tulingxueyuan.mall.modules.ums.model.UmsAdmin;
import com.tulingxueyuan.mall.modules.ums.model.UmsRole;
import com.tulingxueyuan.mall.modules.ums.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/5 13:08
 * @Version 1.0
 */
public class AdminDetails implements UserDetails {

    private List<UmsRole> roleList;

    private UmsAdmin umsAdmin;

    public UmsAdmin getUmsAdmin() {
        return umsAdmin;
    }

    public AdminDetails(List<UmsRole> roleList, UmsAdmin umsAdmin) {
        this.roleList = roleList;
        this.umsAdmin = umsAdmin;
    }

    /**
     * 用户权限配置
     * @Date 2021/12/5 15:45
     * @return java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //jdk操作集合的新方法  返回list<SimpleGrantedAuthority>  SimpleGrantedAuthority是GrantedAuthority子类
        return roleList.stream()
                .map(role->new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus()==1;
    }
}
