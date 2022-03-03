package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.entity.ProductAttribute;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品分类中的添加功能中的筛选属性下拉级联列表数据
 * @Author 86131
 * @Date 2021/11/19 12:39
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "级联列表数据对象",description = "商品分类中的添加（编辑）中的筛选属性选项的下拉级联列表数据")
public class ProductAttributeCategoryDTO {

    //商品类型id
    private Long id;

    //商品类型名称
    private String name;

    //商品类型对应的商品属性或参数（其实应该也给ProductAttribute建一个dto类，这里偷懒了）
    private List<ProductAttribute> productAttributeList;

}
