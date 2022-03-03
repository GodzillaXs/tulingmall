package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用于商品列表编辑功能初始化原数据
 * @Author 86131
 * @Date 2021/11/20 21:51
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "商品修改初始化列表的数据传输对象",description = "用于商品列表编辑功能初始化原数据")
public class ProductThreeDTO extends ProductTwoDTO {

    //一级分类id
    private Long cateParentId;
}
