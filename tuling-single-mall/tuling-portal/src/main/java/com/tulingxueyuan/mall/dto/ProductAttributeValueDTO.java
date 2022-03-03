package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.entity.ProductAttributeValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/27 16:53
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "spu数据传输对象",description = "用于商品详情页面展示spu数据")
public class ProductAttributeValueDTO extends ProductAttributeValue {
    @ApiModelProperty(value = "商品属性规格名称")
    private String attrName;
}
