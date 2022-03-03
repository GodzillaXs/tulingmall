package com.tulingxueyuan.component;

import cn.hutool.json.JSONUtil;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.api.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有权限访问时的响应处理,需要用response返回json类型的数据
 * @Author 86131
 * @Date 2021/12/3 16:32
 * @Version 1.0
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // 响应403 没有相关权限
        response.getWriter().println(JSONUtil.parse(CommonResult.failed(ResultCode.FORBIDDEN)));
        response.getWriter().flush();
    }
}