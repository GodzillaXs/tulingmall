package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品分类的添加功能提交后传给服务器的数据
 * @Author 86131
 * @Date 2021/11/19 14:11
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "商品分类的添加功能提交后传给服务器的数据",description = "商品分类的添加功能提交后传给服务器的数据")
public class ProductCategoryDTO extends ProductCategory {
    private List<Long> productAttributeIdList;
}
