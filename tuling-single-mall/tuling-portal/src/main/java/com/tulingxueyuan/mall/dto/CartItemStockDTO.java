package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.entity.CartItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/30 16:40
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "购物车商品项列表和库存的传输对象",description = "仅用于在显示购物车列表数据时多加一个库存字段")
public class CartItemStockDTO extends CartItem{
    @ApiModelProperty(value = "商品项的真实库存（库存减去锁定库存）")
    Integer stock;
}
