package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.HomeGoodsSaleDTO;
import com.tulingxueyuan.mall.dto.HomeMenusBannerDTO;
import com.tulingxueyuan.mall.dto.HomeMenusDTO;
import com.tulingxueyuan.mall.modules.pms.service.ProductCategoryService;
import com.tulingxueyuan.mall.modules.sms.entity.HomeAdvertise;
import com.tulingxueyuan.mall.modules.sms.service.HomeAdvertiseService;
import com.tulingxueyuan.mall.modules.sms.service.HomeCategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页控制器
 * @Author 86131
 * @Date 2021/11/25 21:12
 * @Version 1.0
 */
@RestController
@Api(tags = "HomeController",description = "首页内容管理")
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private HomeAdvertiseService homeAdvertiseService;

    @Autowired
    private HomeCategoryService homeCategoryService;

    /**   
     * 获取首页导航栏信息和导航栏中的广告横幅
     * /home/menus
     * get
     * @Date 2021/11/25 21:25 
     * @return null 
     */
    @GetMapping("/menus_banner")
    public CommonResult getMenus(){
        //分类导航
        List<HomeMenusDTO> list=productCategoryService.getMenus();

        //banner
        List<HomeAdvertise> homeAdvertiseList=homeAdvertiseService.getHomeBanners();

        HomeMenusBannerDTO homeMenusBannerDTO=new HomeMenusBannerDTO();
        homeMenusBannerDTO.setHomeMenusDTOList(list);
        homeMenusBannerDTO.setHomeAdvertiseList(homeAdvertiseList);

        return CommonResult.success(homeMenusBannerDTO);
    }
    
    /**   
     *首页商品分类推荐
     * this.axios.get("/home/goods_sale")
     *
     * @Date 2021/11/26 15:35 
     * @return null 
     */
    @GetMapping("/goods_sale")
    public CommonResult goodsSale(){
       List<HomeGoodsSaleDTO> list= homeCategoryService.goodsSale();
       return CommonResult.success(list);
    }
}
