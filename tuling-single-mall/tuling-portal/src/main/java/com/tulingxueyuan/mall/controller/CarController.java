package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.AddCarDTO;
import com.tulingxueyuan.mall.dto.CartItemStockDTO;
import com.tulingxueyuan.mall.modules.oms.entity.CartItem;
import com.tulingxueyuan.mall.modules.oms.service.CartItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/28 13:59
 * @Version 1.0
 */
@RestController
@Api(tags = "CarController",description = "购物车内容管理")
@RequestMapping("/car")
public class CarController {

    @Autowired
    CartItemService cartItemService;
    
    /**
     * 将商品加入购物车
     * this.axios.post("/car/add", {
     *    productId: this.id,
     *    productSkuId: this.skuId,
     *    quantity: 1,
     *  })
     *
     * @Date 2021/11/28 13:59 
     * @return null 
     */
    @PostMapping("/add")
    public CommonResult add(
            @RequestBody AddCarDTO addCarDTO
    ){
        boolean result=cartItemService.add(addCarDTO);
        if (result){
           return CommonResult.success(result);
        }else {
           return CommonResult.failed();
        }
    }

    /**
     * 初始化状态栏的购物车商品数量
     * this.axios.get('/car/products/sum')
     *
     * @Date 2021/11/28 15:58
     * @return null
     */
    @GetMapping("/products/sum")
    public CommonResult getCarProductSum(){
       Integer count=cartItemService.getCarProductSum();
       return CommonResult.success(count);
    }

    /**
     *   购物车页面初始化
     * this.axios.get('/car/list')
     * @Date 2021/11/29 11:05
     * @return null
     */
    @GetMapping("/list")
    public CommonResult getList(){
        List<CartItemStockDTO> list=cartItemService.getList();
        return CommonResult.success(list);
    }

    /**
     * 购物车中修改商品数量（+，-）
     * this.axios.post('/car/update/quantity',Qs.stringify({
     *       id:item.id,
     *       quantity:item.quantity
     *       })
     * @Date 2021/11/29 11:20
     * @return null
     */
    @PostMapping("/update/quantity")
    public CommonResult updateQuantity(
            @RequestParam("id") Long id,
            @RequestParam("quantity") Integer quantity
    ){
        boolean result=cartItemService.updateQuantity(id,quantity);
        if (result){
            return  CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     *  删除
     * this.axios.post('/car/delete',Qs.stringify({
     *      ids:item.id
     *      })
     * @Date 2021/11/29 11:32
     * @return null
     */
    @PostMapping("/delete")
    public CommonResult delete(
            @RequestParam("ids") Long ids
    ){
        boolean result=cartItemService.delete(ids);
        if (result){
            return  CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 当购物车中的商品数量已经达到库存值的时候，不允许通过商品详情页面的加入购物车按钮往购物车添加该商品
     * this.axios.get("/car/get_quantity",{productSkuId: this.skuId,productId:this.id})
     * @Date 2021/11/30 22:09
     * @return null
     */
    @GetMapping("/get_quantity")
    public CommonResult getQuantity(
            @RequestParam("productSkuId") Long productSkuId,
            @RequestParam("productId") Long productId
    ){
        Integer quantity=cartItemService.getQuantity(productSkuId,productId);
        return CommonResult.success(quantity);
    }

}
