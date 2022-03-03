package com.tulingxueyuan.mall.modules.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-27
 */
public interface MemberService extends IService<Member> {

    Member register(Member umsMemberParam);

    Member login(@NotBlank(message = "用户名不能为空") String username, @NotBlank(message = "密码不能为空") String password);

    Member getMemberByUsername(String username);

    Member getCurrentMember();

    /**
     * SpringSecurity跨模块查询当前用户信息
     * @Date 2021/12/5 10:34
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    UserDetails loadUserByUsername(String username);
}
