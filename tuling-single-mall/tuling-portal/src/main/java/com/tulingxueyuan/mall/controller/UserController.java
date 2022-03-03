package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.util.JwtTokenUtil;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/27 20:09
 * @Version 1.0
 */
@RestController
@Api(tags = "UserController",description = "前台用户登录注册页面内容管理")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;



    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Member> register(@Validated @RequestBody Member umsMemberParam) {
        Member useMember = memberService.register(umsMemberParam);
        if (useMember == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(useMember);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@Validated  Member umsMemberParam) {
        Member login = memberService.login(umsMemberParam.getUsername(), umsMemberParam.getPassword());
        if (login == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
//        //session方式
//        session.setAttribute(ComConstants.FLAG_MEMBER_USER,login);
//        System.out.println("session id is : "+session.getId());
//        return CommonResult.success(login);

        // jwt方式
        String token = jwtTokenUtil.generateUserNameStr(login.getUsername());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        tokenMap.put("tokenHeader",tokenHeader);
        return CommonResult.success(tokenMap);

    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
//        //session方式
//        session.setAttribute(ComConstants.FLAG_CURRENT_USER,null);
        //jwt
//        JwtTokenUtil.currentUsername.set(null);
        return CommonResult.success(null);
    }
}
