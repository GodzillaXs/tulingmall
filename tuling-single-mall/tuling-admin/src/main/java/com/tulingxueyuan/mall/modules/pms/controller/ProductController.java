package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductDTO;
import com.tulingxueyuan.mall.dto.ProductThreeDTO;
import com.tulingxueyuan.mall.dto.ProductTwoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Product;
import com.tulingxueyuan.mall.modules.pms.entity.SkuStock;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductMapper;
import com.tulingxueyuan.mall.modules.pms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    /**
     * url:'/product/list',
     * method:'get',
     * params:{
     *        keyword: null,
     *        pageNum: 1,
     *        pageSize: 5,
     *        publishStatus: null,
     *        verifyStatus: null,
     *        productSn: null,
     *        productCategoryId: null,
     *        brandId: null
     *        };
     *
     * 关于axio传递过来的参数是json类型还是formdata类型的三种猜测情况：
     *          如果js文件中传递的参数写的是data，代表是json类型
     *          如果js文件中传递的参数写的是params，代表是fromdata类型
     *          前两种我不确定对不对，但是第三种：如果看到了new URLSearchParams();那么一定是formdata类型
     *
     * @Date 2021/11/19 20:37
     * @return null
     */
    @GetMapping("list")
    public CommonResult list(ProductDTO productDTO){
        Page page=productService.list(productDTO);

        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 逻辑删除
     * url:'/product/update/deleteStatus',
     * method:'post',
     * params:ids
     *
     * @Date 2021/11/19 21:38
     * @return null
     */
    @PostMapping("/update/deleteStatus")
    public CommonResult updateDeleteStatus(
            @RequestParam("ids") List<Long> ids
    ){
        boolean result = productService.removeByIds(ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     *   新品状态栏
     *   export function updateNewStatus(params) {
     *    return request({
     *    url:'/product/update/newStatus',
     *    method:'post',
     *    params:params
     *    })
     *    }
     * @Date 2021/11/19 22:03
     * @return null
     */
    @PostMapping("/update/newStatus")
    public CommonResult updateNewStatus(
            @RequestParam("newStatus") Integer newStatus,
            @RequestParam("ids") List<Long> ids
    ){
        boolean result=productService.updateStatus(newStatus,ids,Product::getNewStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**    推荐状态栏
     *    export function updateRecommendStatus(params) {
     *    return request({
     *    url:'/product/update/recommendStatus',
     *    method:'post',
     *    params:params
     *    })
     *    }
     * @Date 2021/11/19 22:03
     * @return null
     */
    @PostMapping("/update/recommendStatus")
    public CommonResult updateRecommendStatus(
            @RequestParam("recommendStatus") Integer recommendStatus,
            @RequestParam("ids") List<Long> ids
    ){
        boolean result=productService.updateStatus(recommendStatus,ids,Product::getRecommandStatus);//这里应该是数据库表的列名写错了一个字母getRecommandStatus，但是改表名太麻烦，就不改了
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     *    上架状态栏
     *    export function updatePublishStatus(params) {
     *    return request({
     *    url:'/product/update/publishStatus',
     *    method:'post',
     *    params:params
     *    })
     *    }
     *
     * @Date 2021/11/19 22:03
     * @return null
     */
    @PostMapping("/update/publishStatus")
    public CommonResult updatePublishStatus(
            @RequestParam("publishStatus") Integer publishStatus,
            @RequestParam("ids") List<Long> ids
    ){
        boolean result=productService.updateStatus(publishStatus,ids,Product::getPublishStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 添加商品
     * url:'/product/create',
     * method:'post',
     * data:data
     *
     * @Date 2021/11/20 19:16
     * @return null
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody ProductTwoDTO productTwoDTO){
        boolean result=productService.create(productTwoDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改前的数据初始化
     * url:'/product/updateInfo/'+id,
     * method:'get',
     * @Date 2021/11/20 21:45
     * @return null
     */
    @GetMapping("/updateInfo/{id}")
    public CommonResult updateInfoById(
            @PathVariable("id") Long id
    ){
        ProductThreeDTO productThreeDTO= productService.updateInfoById(id);
        return CommonResult.success(productThreeDTO);
    }

    /**
     * 修改
     * url:'/product/update/'+id,
     * method:'post',
     * data:data
     * @Date 2021/11/20 22:56
     * @return null
     */
    @PostMapping("/update/{id}")
    public CommonResult update(
            @RequestBody ProductTwoDTO productTwoDTO
    ){
        boolean result=productService.update(productTwoDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
//        List<SkuStock> skuStockList = productTwoDTO.getSkuStockList();
//        System.out.println("========="+skuStockList);
//        return CommonResult.success(true);
    }

}

