package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/26 15:44
 * @Version 1.0
 * 商品分类没有在后台管理系统中实现，只是在数据库建了一张表存了几条固定的二级商品类型数据简单表示推荐的商品分类
 * 因为只有二级商品分类的类型才有商品，有空可以扩展一下后台的功能
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "首页商品分类推荐的传输数据",description = "首页商品分类推荐的传输数据")
public class HomeGoodsSaleDTO {
    private String categoryName;
    @ApiModelProperty(value = "图片")
    private String pic;
    @ApiModelProperty(value = "链接地址")
    private String url;
    @ApiModelProperty(value = "该分类下的商品")
    private List<ProductDTO> productDTOList;

}
