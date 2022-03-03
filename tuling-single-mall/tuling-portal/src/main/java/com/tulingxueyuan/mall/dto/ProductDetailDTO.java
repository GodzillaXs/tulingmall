package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用于商品列表添加功能，服务器接收数据
 * @Author 86131
 * @Date 2021/11/20 19:43
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "商品保存数据传输对象",description = "用于商品列表添加功能接收参数")
public class ProductDetailDTO extends Product {

    //商品属性相关
    @ApiModelProperty(value = "商品属性相关")
    private List<ProductAttributeValueDTO> productAttributeValueDTOList;
    //商品sku库存信息
    @ApiModelProperty(value = "商品sku信息")
    private List<SkuStock> skuStockList;

}
