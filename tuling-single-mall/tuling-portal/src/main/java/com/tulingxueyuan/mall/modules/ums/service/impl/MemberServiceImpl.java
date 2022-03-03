package com.tulingxueyuan.mall.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.common.util.ComConstants;
import com.tulingxueyuan.mall.common.util.JwtTokenUtil;
import com.tulingxueyuan.mall.domain.MemberDetails;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.entity.MemberLoginLog;
import com.tulingxueyuan.mall.modules.ums.mapper.MemberLoginLogMapper;
import com.tulingxueyuan.mall.modules.ums.mapper.MemberMapper;
import com.tulingxueyuan.mall.modules.ums.service.MemberCacheService;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-27
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    MemberLoginLogMapper loginLogMapper;

    @Autowired
    MemberCacheService memberCacheService;

    @Autowired
    PasswordEncoder passwordEncoder;


    /**   
     * @param umsMemberParam    
     * 注册
     * @Date 2021/11/27 21:00 
     * @return com.tulingxueyuan.mall.modules.ums.entity.Member 
     */
    @Override
    public Member register(Member umsMemberParam) {
        Member umsMember = new Member();
        BeanUtils.copyProperties(umsMemberParam, umsMember);
        LocalDateTime localDateTime = LocalDateTime.now();
        umsMember.setCreateTime(localDateTime);
        umsMember.setStatus(1);
        //查询是否有相同用户名的用户
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Member::getUsername,umsMember.getUsername());
        List<Member> umsAdminList = this.list(wrapper);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
//        String encodePassword = BCrypt.hashpw(umsMember.getPassword());
//      hutool工具类的BCrypt.hashpw和此时SpringSecurity的passwordEncoder.encode是相同的加密方式，效果一样，因为passwordEncoder实际上是子类BCryptPasswordEncoder
        String encodePassword = passwordEncoder.encode(umsMember.getPassword());
        umsMember.setPassword(encodePassword);
        baseMapper.insert(umsMember);
        return umsMember;
    }

    /**   
     * @param username 
     * @param password    
     * 登录
     * @Date 2021/11/27 21:04
     * @return com.tulingxueyuan.mall.modules.ums.entity.Member 
     */
    @Override
    public Member login(@NotBlank(message = "用户名不能为空") String username, @NotBlank(message = "密码不能为空") String password) {
        //密码需要客户端加密后传递
        Member member=null;
        try {
//            umsMember = loadUser(username);
            UserDetails userDetails = loadUserByUsername(username);
            member=((MemberDetails) userDetails).getMember();
//            if(!BCrypt.checkpw(password,member.getPassword())) hutool工具,效果是一样的
            if(!passwordEncoder.matches(password,member.getPassword())){
                Asserts.fail("密码不正确");
                System.out.println("账户有问题");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            insertLoginLog(username);
        } catch (Exception e) {
            Asserts.fail("登录异常:"+e.getMessage());
        }
        return member;
    }

    /**   
     * @param username
     * 获取用户信息
     * @Date 2021/11/27 21:21 
     * @return com.tulingxueyuan.mall.modules.ums.entity.Member 
     */
    public Member loadUser(String username) {
        //获取用户信息
        Member umsMember = getMemberByUsername(username);
        if (umsMember != null) {
            // 查询用户访问资源，暂留， 后续改动
            // List<UmsResource> resourceList = getResourceList(admin.getId());
            return umsMember;
        }
        throw new ApiException("用户不存在");
    }

    /**   
     * @param username    
     * 根据用户名获取用户信息
     * @Date 2021/11/27 21:22
     * @return com.tulingxueyuan.mall.modules.ums.entity.Member 
     */
    @Override
    public Member getMemberByUsername(String username) {
        Member umsMember = memberCacheService.getMember(username);
        if(umsMember!=null) return  umsMember;
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Member::getUsername,username);
        List<Member> list = list(wrapper);
        if (list != null && list.size() > 0) {
            umsMember = list.get(0);
            memberCacheService.setMember(umsMember);
            return umsMember;
        }
        return null;
    }

    /**
     * 获取当前用户的信息
     * @Date 2021/11/28 14:29
     * @return com.tulingxueyuan.mall.modules.ums.entity.Member
     */
    @Override
    public Member getCurrentMember() {
        //SpringSecurity
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails =(MemberDetails) authentication.getPrincipal();
        return memberDetails.getMember();
        //jwt方式
//        String userName = JwtTokenUtil.currentUsername.get();
//        if (StrUtil.isNotBlank(userName)){
//            Member member = getMemberByUsername(userName);
//            return member;
//        }
//        return null;
    }

    /**
     *
     * SpringSecurity跨模块查询当前用户信息
     * @Date 2021/12/5 10:36
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = getMemberByUsername(username);
        if (member!=null){
            return new MemberDetails(member);
        }
        throw new ApiException("用户名或密码错误");
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        Member umsMember = getMemberByUsername(username);
        if(umsMember==null) return;
        MemberLoginLog loginLog = new MemberLoginLog();
        loginLog.setId(umsMember.getId());
        loginLog.setCreateTime(LocalDateTime.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }
}
