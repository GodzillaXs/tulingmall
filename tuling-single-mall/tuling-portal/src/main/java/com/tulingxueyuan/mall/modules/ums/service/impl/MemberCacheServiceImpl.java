package com.tulingxueyuan.mall.modules.ums.service.impl;

import com.tulingxueyuan.mall.common.service.RedisService;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.service.MemberCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/27 21:53
 * @Version 1.0
 */
@Service
public class MemberCacheServiceImpl implements MemberCacheService {

    @Autowired
    RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;

    @Override
    public Member getMember(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + username;
        return (Member) redisService.get(key);
    }

    @Override
    public void setMember(Member member) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getUsername();
        redisService.set(key, member, REDIS_EXPIRE);
    }
}
