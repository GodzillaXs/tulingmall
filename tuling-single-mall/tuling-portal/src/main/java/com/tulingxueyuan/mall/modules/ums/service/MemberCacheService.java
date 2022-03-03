package com.tulingxueyuan.mall.modules.ums.service;

import com.tulingxueyuan.mall.modules.ums.entity.Member;

public interface MemberCacheService {
    /**
     * 获取缓存后台用户信息
     */
    Member getMember(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setMember(Member member);
}
