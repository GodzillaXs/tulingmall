package com.tulingxueyuan.component;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/5 16:07
 * @Version 1.0
 */
public interface SecurityResourceRoleSource {
    /**
     * 获取所有 资源以及对应的角色列表
     // key: 资源： /product/**
     // value: 角色
     * @return
     */
    Map<String,List<String>> getResourceRole();
}
