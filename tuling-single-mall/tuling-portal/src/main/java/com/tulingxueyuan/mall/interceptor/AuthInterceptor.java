package com.tulingxueyuan.mall.interceptor;

import cn.hutool.core.util.StrUtil;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.common.util.ComConstants;
import com.tulingxueyuan.mall.common.util.JwtTokenUtil;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作用： 验证 用户是否登录
 * 注：已暂时在配置文件中关闭，换为SpringSecurity进行权限管理
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    // 配置文件中的白名单secure.ignored.urls
    private List<String> urls;

    @Autowired
    private MemberService memberService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、不需要登录就可以访问的路径——白名单
        // 获取当前请求   /admin/login
        String requestURI = request.getRequestURI();
        // Ant方式路径匹配 /**  ？  _
        PathMatcher matcher = new AntPathMatcher();
        for (String ignoredUrl : urls) {
            if(matcher.match(ignoredUrl,requestURI)){
                return true;
            }
        }

        //jwt方式（122集）
        //获取jwt令牌
        String jwt = request.getHeader(tokenHeader);
        //判断是否存在以及开头是否加了我们配置的tokenHead
        if (StrUtil.isBlank(jwt) && !jwt.startsWith(tokenHead)){
            //抛出401异常信息发送给前端，前端设置了拦截器，接收到401状态码会跳转到登录页面
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        //解密 先删除jwt令牌中的tokenHead再解密，因为这个是我们加上去的，不属于待解密字符串内容
        jwt=jwt.substring(tokenHead.length());
        String userName = jwtTokenUtil.getUserNameFromToken(jwt);
        if (StrUtil.isBlank(userName)){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        Member member = memberService.getMemberByUsername(userName);
        if (member==null){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        //将username存入JwtTokenUtil类的线程安全的静态变量中
//        JwtTokenUtil.currentUsername.set(userName); 在JwtTokenUtil注释了currentUsername，需要复习的时候再解除注释



//        //session方式
//        //使用session方式需要解决特殊情况产生的BUG,服务器重启导致原来session丢失，但客户端的cook还存在，
//        //这时需要将新session和cook对应的数据重新联系起来。（但是解决这个问题后我发现又出现了新的问题，由此看来session方式确实不太可靠）
//        String userName = request.getHeader("Authorization");
//        if (!StrUtil.isBlank(userName)){
//            Member member = memberService.getMemberByUsername(userName);
//            request.getSession().setAttribute(ComConstants.FLAG_MEMBER_USER,member);
//            return true;
//        }
//        //2、未登录用户，抛出401异常信息发送给前端，前端设置了拦截器，接收到401状态码会跳转到登录页面
//        if (null == request.getSession().getAttribute(ComConstants.FLAG_MEMBER_USER)) {
//            throw new ApiException(ResultCode.UNAUTHORIZED);
//        }
        return true;
    }


    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
