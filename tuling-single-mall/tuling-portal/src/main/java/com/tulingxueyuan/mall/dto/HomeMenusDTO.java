package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/25 21:24
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "首页商品分类导航栏数据",description = "首页商品分类导航栏数据")
public class HomeMenusDTO {
    private Long id;
    private String name;
    private List<ProductDTO> productDTOList;
}
