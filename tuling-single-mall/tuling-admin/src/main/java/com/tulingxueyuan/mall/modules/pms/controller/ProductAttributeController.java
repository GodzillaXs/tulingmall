package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttribute;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;
import com.tulingxueyuan.mall.modules.pms.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/productAttribute")
public class ProductAttributeController {

    @Autowired
    ProductAttributeService productAttributeService;

    /**
     * 获取分页数据
     * url:'/productAttribute/list/'+cid,
     * method:'get',
     * params:cid,selectType,pageNum,pageSize
     *
     * @Date 2021/11/18 14:37
     * @return null
     */
    @GetMapping("list/{cid}")
    public CommonResult<CommonPage<ProductAttribute>> getPageF(
            @PathVariable("cid") Long cid,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){
        Page page=productAttributeService.getPageF(cid,type,pageNum,pageSize);
        //将page转换为commonPage再返回结果
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 添加
     * url:'/productAttribute/create',
     * method:'post',
     * data:data
     *
     * @Date 2021/11/18 15:17
     * @return null
     */
    @PostMapping("/create")
    public CommonResult create(
            @RequestBody ProductAttribute productAttribute
    ){
        boolean result = productAttributeService.create(productAttribute);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改前先获取数据
     * url:'/productAttribute/'+id,
     * method:'get'
     *
     * @Date 2021/11/18 15:30
     * @return null
     */
    @GetMapping("/{id}")
    public CommonResult getById(
            @PathVariable("id") Long id
    ){
        ProductAttribute result = productAttributeService.getById(id);
        return CommonResult.success(result);
    }


    /**
     * 修改
     * url:'/productAttribute/update/'+id,
     * method:'post',
     * data:data   
     * 
     * @Date 2021/11/18 15:29 
     * @return null 
     */
    @PostMapping("/update/{id}")
    public CommonResult updateById(
            @RequestBody ProductAttribute productAttribute
    ){
        boolean result = productAttributeService.updateById(productAttribute);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除
     * url:'/productAttribute/delete',
     * method:'post',
     * data:data
     *
     * @Date 2021/11/18 15:48
     * @return null
     */
    @PostMapping("/delete")
    public CommonResult deleteById(
            @RequestParam("ids") List<Long> ids
    ){
        boolean result = productAttributeService.deleteById(ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 根据商品类型id获取关联的商品属性，用于商品分类的编辑功能中的初始化部分
     * url:'/productAttribute/attrInfo/'+productCategoryId,
     * method:'get'
     *
     * @Date 2021/11/19 15:13
     * @return null
     */
    @GetMapping("/attrInfo/{productCategoryId}")
    public CommonResult getRelationAttrInfo(
            @PathVariable("productCategoryId") Long productCategoryId
    ){
        List<RelationAttrInfoDTO> result = productAttributeService.getRelationAttrInfo(productCategoryId);
        return CommonResult.success(result);
    }
}

