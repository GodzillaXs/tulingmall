package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.ums.model.UmsResource;
import com.tulingxueyuan.mall.modules.ums.model.UmsRole;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/5 16:23
 * @Version 1.0
 */
@Data
public class ResourceRoleDTO extends UmsResource {

    private List<UmsRole> roleList;
}
