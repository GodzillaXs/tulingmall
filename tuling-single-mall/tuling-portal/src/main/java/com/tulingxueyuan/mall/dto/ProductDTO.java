package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/25 21:28
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "商品的数据传输对象",description = "商品的数据传输对象")
public class ProductDTO {
    private Long id;
    private String name;
    @ApiModelProperty(value = "图片")
    private String pic;
    @ApiModelProperty(value = "市场价")
    private BigDecimal originalPrice;
    @ApiModelProperty(value = "促销价格")
    private BigDecimal promotionPrice;
    @ApiModelProperty(value = "副标题")
    private String subTitle;
    @ApiModelProperty(value = "商品不同参数的价格是否相同，0代表不相同，1代表相同，如果价格不相同就会在显示价格时在后面加一个起字")
    private Integer sub;
    @ApiModelProperty(value = "正常价格")
    private BigDecimal price;
}
