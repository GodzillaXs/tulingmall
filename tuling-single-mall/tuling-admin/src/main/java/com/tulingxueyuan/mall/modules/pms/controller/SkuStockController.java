package com.tulingxueyuan.mall.modules.pms.controller;


import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.entity.SkuStock;
import com.tulingxueyuan.mall.modules.pms.service.SkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku的库存 前端控制器
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/sku")
public class SkuStockController {
    @Autowired
    private SkuStockService skuStockService;

    /**
     * 修改商品的sku库存的对话框的商品sku数据初始化
     * return request({
     *      url:'/sku/'+pid,
     *      method:'get',
     *      params:
     *          {keyword:this.editSkuInfo.keyword}
     *     })
     * @Date 2021/12/8 16:35
     * @return null
     */
    @GetMapping("/{pid}")
    public CommonResult getSkuByProductId(
            @PathVariable("pid") Long pid,
            String keyword //keyword有可能为空，所以不能加@RequestParam ,加了@RequestParam 则此参数必须存在，否则就报400
    ){
        List<SkuStock> list=skuStockService.getSkuByProductId(pid,keyword);
        return CommonResult.success(list);
    }


    /**
     * 修改商品的sku库存
     *  return request({
     *       url:'/sku/update/'+pid,
     *       method:'post',
     *       data: this.editSkuInfo.stockList
     *       })
     *
     * @Date 2021/12/8 18:14
     * @return null
     */
    @PostMapping("/update/{pid}")
    public CommonResult updateSkuByPid(
            @RequestBody List<SkuStock> list
    ){
        boolean result = skuStockService.updateBatchById(list);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
}

