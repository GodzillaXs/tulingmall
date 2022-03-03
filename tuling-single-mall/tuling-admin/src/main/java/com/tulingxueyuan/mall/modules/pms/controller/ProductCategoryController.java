package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductCategoryDTO;
import com.tulingxueyuan.mall.dto.ProductCategoryTwoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;
import com.tulingxueyuan.mall.modules.pms.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    /**
     * 获取分页数据
     *    url:'/productCategory/list/'+parentId,
     *    method:'get',
     *    params:
     *              parentId
     *              listQuery: {
     *                        pageNum: 1,
     *                        pageSize: 5
     *                        }
     * @Date 2021/11/17 16:53
     * @return CommonResult中的CommonPage作为返回的分页数据类型
     */
    @GetMapping("list/{parentId}")
    public CommonResult<CommonPage<ProductCategory>> getPageF(
            @PathVariable("parentId") Long parentId,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){

        Page page=productCategoryService.getPageF(parentId,pageNum,pageSize);
        //将page转换为commonPage再返回结果
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     *修改导航栏
     *      url:'/productCategory/update/navStatus',
     *      method:'post',
     *      data:
     *          data.append('ids',ids);
     *          data.append('navStatus',row.navStatus);
     * @Date 2021/11/17 19:31
     * @return null
     */
    @PostMapping("/update/navStatus")
    public CommonResult updateNavStatus(
            @RequestParam("ids") List<Integer> ids,
            @RequestParam("navStatus") Integer navStatus
            ){

        boolean result = productCategoryService.updateNavStatus(ids, navStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改显示栏
     *          url:'/productCategory/update/showStatus',
     *          method:'post',
     *          data:
     *              data.append('ids',ids);
     *              data.append('showStatus',row.showStatus);
     *
     * @Date 2021/11/17 19:53
     * @return null
     */
    @PostMapping("/update/showStatus")
    public CommonResult updateShowStatus(
            @RequestParam("ids") List<Integer> ids,
            @RequestParam("showStatus") Integer showStatus
    ){

        boolean result = productCategoryService.updateShowStatus(ids, showStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除
     *          url:'/productCategory/delete/'+id,
     *          method:'post'
     *
     * @Date 2021/11/17 19:53
     * @return null
     */
    @PostMapping("/delete/{id}")
    public CommonResult deleteById(
           @PathVariable("id") Long id
    ){
        boolean result = productCategoryService.removeById(id);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 添加
     *    url:'/productCategory/create',
     *    method:'post',
     *    data:data
     *
     * @Date 2021/11/17 19:53
     * @return null
     */
    @PostMapping("/create")
    public CommonResult create(
          @RequestBody ProductCategoryDTO productCategoryDTO
    ){
        boolean result = productCategoryService.create(productCategoryDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改前先获取原数据
     *          url:'/productCategory/'+id,
     *          method:'get',
     *
     * @Date 2021/11/17 19:53
     * @return null
     */
    @GetMapping("/{id}")
    public CommonResult getById(
           @PathVariable("id") Long id
    ){
        ProductCategory result = productCategoryService.getById(id);
        return CommonResult.success(result);
    }

    /**
     * 修改
     *          url:'/productCategory/update/'+id,
     *          method:'post',
     *          data:data
     *这里参数后面带的id实际上没有用，只是为了遵循rest风格的规则，修改时是根据data也就是productCategory里面的id修改的
     * @Date 2021/11/17 19:53
     * @return null
     */
    @PostMapping("/update/{id}")
    public CommonResult updateById(
           @RequestBody ProductCategoryDTO productCategoryDTO
    ){
        boolean result = productCategoryService.updateByIdF(productCategoryDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 商品列表初始化时的商品分类的级联数据
     * url:'/productCategory/list/withChildren',
     * method:'get'
     *
     * @Date 2021/11/19 18:59
     * @return null
     */
    @GetMapping("/list/withChildren")
    public CommonResult listWithChildren(){
        List<ProductCategoryTwoDTO> result =productCategoryService.listWithChildren();
        return CommonResult.success(result);
    }

}

