package com.tulingxueyuan.component;

import cn.hutool.core.util.StrUtil;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter:保证一次请求的过滤只执行一次，有些服务器因为版本不兼容或者自身的设置可能会执行多次
 * @Author 86131
 * @Date 2021/12/5 9:58
 * @Version 1.0
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取jwt令牌
        String jwt = request.getHeader(tokenHeader);
        //判断是否存在以及开头是否加了我们配置的tokenHead
        if (!StrUtil.isBlank(jwt) && jwt.startsWith(tokenHead)){
            //解密 先删除jwt令牌中的tokenHead再解密，因为这个是我们加上去的，不属于待解密字符串内容
            jwt=jwt.substring(tokenHead.length());
            String userName = jwtTokenUtil.getUserNameFromToken(jwt);
            if (!StrUtil.isBlank(userName)){
                //实际传过来的是UserDetails的实现类MemberDetails
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (userDetails!=null){
                    //生成SpringSecurity通过认证标识
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //将认证放入Security上下文对象中
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //放行，没有认证的自然会被后面的security过滤器给处理掉，然后根据我们设置的没有登录时的处理类来返回响应
        filterChain.doFilter(request,response);
    }
}
