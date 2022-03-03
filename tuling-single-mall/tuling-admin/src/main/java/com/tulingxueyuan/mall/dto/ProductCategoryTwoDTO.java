package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * 商品列表中初始化时的商品分类级联列表
 * @Author 86131
 * @Date 2021/11/19 19:04
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "级联列表数据对象",description = "用于商品列表中初始化时的商品分类级联列表")
public class ProductCategoryTwoDTO {
    //商品类型id
    private Long id;

    //商品类型名称
    private String name;

    //商品分类二级级联数据
    private List<ProductCategory> children;

}
