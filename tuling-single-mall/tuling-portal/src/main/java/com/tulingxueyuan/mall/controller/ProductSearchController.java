package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Product;
import com.tulingxueyuan.mall.modules.pms.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/7 10:28
 * @Version 1.0
 */
@RestController
@Api(tags = "ProductSearchController",description = "根据搜索栏或者点击推荐栏的商品分类获得商品列表")
@RequestMapping("/esProduct/search")
public class ProductSearchController {

    @Autowired
    private ProductService productService;
    
    /**
     * 根据搜索栏或者点击推荐栏的商品分类获得商品列表
     * this.axios.post('/esProduct/search/simple',Qs.stringify({keyword : this.keywords},{indices: false}),
     * {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then((res)=>{
     * this.productList=res.list;
     * });
     * @Date 2021/12/7 10:29
     * @return null 
     */
    @PostMapping("/simple")
    public CommonResult simpleSearch(
           @RequestParam("keyword") String keyword
    ){
        List<ProductDTO> list=productService.simpleSearch(keyword);
        return CommonResult.success(list);
    }

}
