package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/19 20:42
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "商品列表筛选条件",description = "用于商品列表显示分页数据")
public class ProductDTO {
    private  String keyword;
    private Integer pageNum;
    private Integer pageSize;
    private Long productCategoryId;
    private Long brandId;
    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Integer publishStatus;
    @ApiModelProperty(value = "审核状态： 0->未审核 1->审核通过")
    private Integer verifyStatus;
    @ApiModelProperty(value = "货号")
    private String productSn;

}
