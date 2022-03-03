package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductAttributeCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.service.ProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品类型 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/productAttribute/category/")
public class ProductAttributeCategoryController {

    @Autowired
    ProductAttributeCategoryService productAttributeCategoryService;
    
    /**
     * 获取分页数据
     *url:'/productAttribute/category/list',
     *method:'get',
     *params:pageNum,pageSize
     *
     * @Date 2021/11/18 13:52 
     * @return null 
     */
    @GetMapping("list")
    public CommonResult<CommonPage<ProductAttributeCategory>> getPageF(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){
        Page page=productAttributeCategoryService.getPageF(pageNum,pageSize);
        //将page转换为commonPage再返回结果
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 添加
     * url:'/productAttribute/category/create',
     *method:'post',
     *data:ProductAttributeCategory
     *
     *
     */
    @PostMapping("/create")
    public CommonResult create(ProductAttributeCategory productAttributeCategory){
        boolean result = productAttributeCategoryService.add(productAttributeCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     *修改
     * url:'/productAttribute/category/update/'+id,
     * method:'post',
     * data:ProductAttributeCategory
     * @Date 2021/11/18 14:19
     * @return null
     */
    @PostMapping("/update/{id}")
    public CommonResult updateById(ProductAttributeCategory productAttributeCategory){
        boolean result = productAttributeCategoryService.updateById(productAttributeCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     *删除
     * url:'/productAttribute/category/delete/'+id,
     * method:'get'
     * @Date 2021/11/18 14:19
     * @return null
     */
    @GetMapping("/delete/{id}")
    public CommonResult deleteById(@PathVariable("id")Long id){
        boolean result = productAttributeCategoryService.removeById(id);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 商品分类中的添加功能中的筛选属性下拉级联列表数据
     * url:'/productAttribute/category/list/withAttr',
     * method:'get'
     *
     * @Date 2021/11/19 12:31
     * @return null
     */
    @GetMapping("/list/withAttr")
    public CommonResult listWithAttr(){
        List<ProductAttributeCategoryDTO> result = productAttributeCategoryService.listWithAttr();
        return CommonResult.success(result);
    }




}

