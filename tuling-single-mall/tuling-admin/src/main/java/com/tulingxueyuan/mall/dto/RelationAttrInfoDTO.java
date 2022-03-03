package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @Author 86131
 * @Date 2021/11/19 15:26
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "中间表")
public class RelationAttrInfoDTO {
    //商品类型id
    private Long attributeCategoryId;
    //商品属性id
    private Long attributeId;
}
