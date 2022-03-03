package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/30 19:31
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="确认订单页面初始化传输对象", description="确认订单页面初始化传输对象")
public class OrderDTO {
    @ApiModelProperty(value = "购物车中选中的商品项id")
    private List<Long> itemIds;

    @ApiModelProperty(value = "通过商品详情页立即购买选项过来则有值，商品id")
    private Long productId;

    @ApiModelProperty(value = "通过商品详情页立即购买选项过来则有值，商品具体sku的Id")
    private Long skuId;
}
