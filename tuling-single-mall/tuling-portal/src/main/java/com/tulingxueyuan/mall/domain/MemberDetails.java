package com.tulingxueyuan.mall.domain;

import com.tulingxueyuan.mall.modules.ums.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * SpringSecurity跨模块查询当前用户信息
 * @Author 86131
 * @Date 2021/12/5 10:38
 * @Version 1.0
 */
public class MemberDetails implements UserDetails {

    @Autowired
    private Member member;

    public Member getMember() {
        return member;
    }

    public MemberDetails(Member member) {
        this.member = member;

    }

    /**
     * 目前前台除了白名单，没有其他的权限需要配置
     * @Date 2021/12/5 10:44
     * @return java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
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
        return member.getStatus()==1;
    }
}
