package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/28 14:06
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "添加购物车参数接收对象",description = "添加购物车参数接收对象")
public class AddCarDTO {
    @ApiModelProperty(value = "商品ID")
    private Long productId;
    @ApiModelProperty(value = "商品具体选中的SKU的ID")
    private Long productSkuId;
    private Integer quantity;
}
