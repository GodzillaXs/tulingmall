package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductDetailDTO;
import com.tulingxueyuan.mall.modules.pms.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品详情页控制器
 * @Author 86131
 * @Date 2021/11/27 15:39
 * @Version 1.0
 */
@RestController
@Api(tags = "DetailController",description = "商品详情页内容管理")
@RequestMapping("/product")
public class DetailController {

    @Autowired
    private ProductService productService;

    /**
     * 页面初始化
     * this.axios.get(`/product/detail/${this.id}`)
     *
     * @Date 2021/11/27 15:41 
     * @return null 
     */
    @GetMapping("/detail/{id}")
    public CommonResult getProductDetail(
            @PathVariable("id") Long id
    ){
        ProductDetailDTO productDetail=productService.getProductDetail(id);
        return CommonResult.success(productDetail);
    }
}
